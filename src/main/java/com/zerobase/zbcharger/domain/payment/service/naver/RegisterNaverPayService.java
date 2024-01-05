package com.zerobase.zbcharger.domain.payment.service.naver;

import static com.zerobase.zbcharger.domain.payment.api.naver.dto.constant.NaverPayApiResponseCode.INVALID_MERCHANT;
import static com.zerobase.zbcharger.domain.payment.api.naver.dto.constant.NaverPayApiResponseCode.MAINTENANCE_ONGOING;

import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.payment.api.naver.NaverPayApi;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.ApprovalRecurrentRegistrationResponseBody;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.NaverPayResponse;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.Recurrent;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.SearchRegisteredRecurrentRequest;
import com.zerobase.zbcharger.domain.payment.dao.PaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.dto.NaverPayCallback;
import com.zerobase.zbcharger.domain.payment.entity.NaverPay;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.exception.RegisterPaymentException;
import com.zerobase.zbcharger.domain.payment.service.RegisterPaymentService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 네이버페이 정기/반복결제 등록 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterNaverPayService extends RegisterPaymentService<NaverPayCallback> {

    private final static String SUCCESS = "Success";

    private final PaymentMethodRepository paymentMethodRepository;
    private final MemberRepository memberRepository;

    private final NaverPayApi naverPayApi;

    @Value("${payment.naver.client-id}")
    private String clientId;

    @Value("${payment.naver.client-secret}")
    private String clientSecret;

    @Value("${payment.naver.chain-id}")
    private String chainId;

    @Override
    protected PaymentMethod registerInternal(NaverPayCallback callback) {
        isSuccessResult(callback);

        var response = approvalRecurrentRegistration(callback);
        Recurrent recurrent = getRegisteredRecurrent(response.body().recurrentId());

        long memberId = getMemberId(callback.getUserEmail());
        NaverPay payment = createNaverPay(response.body().recurrentId(), recurrent, memberId);

        if (paymentMethodRepository.countByMemberId(memberId) == 0) {
            payment.setPrimary();
        }

        return paymentMethodRepository.save(payment);
    }

    @Override
    protected NaverPayCallback toPaymentCallback(Map<String, String> params) {
        return NaverPayCallback.builder()
            .resultCode(params.get("resultCode"))
            .reserveId(params.get("reserveId"))
            .resultMessage(params.getOrDefault("resultMessage", null))
            .tempReceiptId(params.getOrDefault("tempReceiptId", null))
            .tempReceiptId(params.getOrDefault("recurrentId", null))
            .userEmail(params.get("userEmail"))
            .build();
    }

    /**
     * 콜백 결과 확인
     *
     * @param callback 콜백 파라미터
     */
    private void isSuccessResult(NaverPayCallback callback) {
        if (!SUCCESS.equals(callback.getResultCode())) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("resultCode", callback.getResultCode());
            payload.put("resultMessage", callback.getResultMessage());
            payload.put("reserveId", callback.getReserveId());
            payload.put("recurrentId", callback.getRecurrentId());
            throw new RegisterPaymentException("정기/반복결제 등록 요청 실패", payload);
        }
    }

    /**
     * 정기/반복결제 등록 완료 요청
     *
     * @param callback
     * @return
     */
    private NaverPayResponse<ApprovalRecurrentRegistrationResponseBody> approvalRecurrentRegistration(
        NaverPayCallback callback) {
        var response = naverPayApi.approvalRecurrentRegistration(
            clientId,
            clientSecret,
            chainId,
            UUID.randomUUID().toString(),
            callback.getReserveId(),
            callback.getTempReceiptId());

        isSuccessResultForApprovalRecurrentRegistration(response);

        return response;
    }

    /**
     * 정기/반복결제 등록 완료 요청 결과 확인
     *
     * @param response 응답
     */
    private void isSuccessResultForApprovalRecurrentRegistration(NaverPayResponse<?> response) {
        if (!response.isSuccess()) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("code", response.code());
            payload.put("message", response.message());
            throw new RegisterPaymentException("정기/반복결제 완료 요청 실패", payload);
        }
    }

    /**
     * 등록된 정기/반복결제 조회
     *
     * @param recurrentId 정기/반복결제 등록 번호
     * @return 정기/반복결제 정보
     */
    private Recurrent getRegisteredRecurrent(String recurrentId) {
        var response = naverPayApi.getRegisteredRecurrentList(
            clientId,
            clientSecret,
            chainId,
            SearchRegisteredRecurrentRequest.builder()
                .recurrentId(recurrentId)
                .build());

        if (INVALID_MERCHANT.getValue().equals(response.code())) {
            throw new RegisterPaymentException("유효하지 않은 가맹점");
        }

        if (MAINTENANCE_ONGOING.getValue().equals(response.code())) {
            // TODO: 서비스 점검 이후 재시도 필요
            return null;
        }

        return Arrays.stream(response.body().list())
            .findFirst()
            .orElse(null);
    }

    private Long getMemberId(String mallUserId) {
        return memberRepository.findIdByEmail(mallUserId).orElseThrow(
            () -> {
                Map<String, Object> payload = new HashMap<>();
                payload.put("mallUserId", mallUserId);
                return new RegisterPaymentException("존재하지 않는 회원입니다.", payload);
            });
    }

    private static NaverPay createNaverPay(String recurrentId, Recurrent recurrent, Long memberId) {
        if (recurrent == null) {
            return NaverPay.builder()
                .recurrentId(recurrentId)
                .memberId(memberId)
                .build();
        }

        return NaverPay.builder()
            .recurrentId(recurrentId)
            .memberId(memberId)
            .productCode(recurrent.productCode())
            .primaryPayMeans(recurrent.primaryPayMeans())
            .primaryPayMeansCorpCd(recurrent.primaryPayMeansCorpCd())
            .primaryPayMeansNo(recurrent.primaryPayMeansNo())
            .build();
    }
}
