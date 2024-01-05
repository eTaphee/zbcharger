package com.zerobase.zbcharger.domain.payment.service.smartro;

import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.payment.dao.PaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.dto.SmartroPayCallback;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.entity.SmartroPay;
import com.zerobase.zbcharger.domain.payment.exception.RegisterPaymentException;
import com.zerobase.zbcharger.domain.payment.service.RegisterPaymentService;
import com.zerobase.zbcharger.util.AesUtils;
import com.zerobase.zbcharger.util.ShaUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterSmartroPayService extends RegisterPaymentService<SmartroPayCallback> {

    private static final String SUCCESS = "3001";

    private final PaymentMethodRepository paymentMethodRepository;
    private final MemberRepository memberRepository;

    @Value("${payment.smartro.merchant-key}")
    private String merchantKey;

    @Override
    protected PaymentMethod registerInternal(SmartroPayCallback callback) {
        isSuccessResult(callback);

        String billTokenKey = decodeBillTokenKey(callback.getBillTokenKey());
        String verifyValue = generateVerifyValue(billTokenKey, callback);
        verifyForgery(verifyValue, callback.getVerifyValue());

        Long memberId = getMemberId(callback.getMallUserId());

        SmartroPay payment = SmartroPay.builder()
            .memberId(memberId)
            .orderId(callback.getOrderId())
            .transactionId(callback.getTransactionId())
            .billTokenKey(billTokenKey)
            .issuerCardCode(callback.getIssuerCardCode())
            .issuerCardName(callback.getIssuerCardName())
            .displayCardNo(callback.getDisplayCardNumber())
            .cardExpire(callback.getCardExpire())
            .build();

        if (paymentMethodRepository.countByMemberId(memberId) == 0) {
            payment.setPrimary();
        }

        return paymentMethodRepository.save(payment);
    }

    @Override
    protected SmartroPayCallback toPaymentCallback(Map<String, String> params) {
        return SmartroPayCallback.builder()
            .payMethod(params.get("PayMethod"))
            .merchantId(params.get("Mid"))
            .orderId(params.get("Moid"))
            .transactionId(params.get("Tid"))
            .billTokenKey(params.get("BillTokenKey"))
            .mallUserId(params.get("MallUserId"))
            .resultCode(params.get("ResultCode"))
            .resultMessage(params.get("ResultMsg"))
            .verifyValue(params.get("VerifyValue"))
            .issuerCardCode(params.get("IssuerCardCd"))
            .issuerCardName(params.get("IssuerCardNm"))
            .displayCardNumber(params.get("DisplayCardNo"))
            .cardExpire(LocalDateTime.parse(params.get("CardExpire"),
                DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
            .encodingType(params.get("EncodingType"))
            .returnUrlEncodingUse("Y".equals(params.get("RtnUrlEncUse")))
            .build();
    }

    /**
     * 콜백 결과 확인
     *
     * @param callback 콜백 파라미터
     */
    private void isSuccessResult(SmartroPayCallback callback) {
        if (!SUCCESS.equals(callback.getResultCode())) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("resultCode", callback.getResultCode());
            payload.put("resultMessage", callback.getResultMessage());
            throw new RegisterPaymentException("빌링키 발급 요청 실패", payload);
        }
    }

    /**
     * 빌링키 복호화
     *
     * @param billTokenKey 암호화된 빌링키
     * @return 복호화된 빌링키
     */
    private String decodeBillTokenKey(String billTokenKey) {
        String mKey = merchantKey.substring(0, 32);
        try {
            return AesUtils.decrypt(billTokenKey, mKey);
        } catch (Exception e) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("billTokenKey", billTokenKey);
            payload.put("merchantKey", merchantKey);
            throw new RegisterPaymentException("빌링키 복호화 실패", e, payload);
        }
    }

    /**
     * 콜백 위변조 검증
     *
     * @param generatedVerifiedValue 생성된 위변조 값
     * @param callbackVerifyValue    콜백에서 전달받은 위변조 값
     */
    private void verifyForgery(String generatedVerifiedValue, String callbackVerifyValue) {
        if (!generatedVerifiedValue.equals(callbackVerifyValue)) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("generatedVerifiedValue", generatedVerifiedValue);
            payload.put("callbackVerifyValue", callbackVerifyValue);
            throw new RegisterPaymentException("위변조 검증 실패", payload);
        }
    }

    /**
     * 위변조 검증 값 생성
     *
     * @param billTokenKey 복호화된 빌링키
     * @param callback     콜백 파라미터
     * @return 생성된 위변조 값
     */
    private String generateVerifyValue(String billTokenKey, SmartroPayCallback callback) {
        try {
            return ShaUtils.sha256Base64(
                callback.getMerchantId() + billTokenKey + callback.getDisplayCardNumber()
                    + callback.getResultCode() + "SMARTRO!@#");
        } catch (Exception e) {
            throw new RegisterPaymentException("위변조 검증 값 생성 실패", e);
        }
    }

    private Long getMemberId(String mallUserId) {
        return memberRepository.findIdByEmail(mallUserId).orElseThrow(
            () -> {
                Map<String, Object> payload = new HashMap<>();
                payload.put("mallUserId", mallUserId);
                return new RegisterPaymentException("존재하지 않는 회원입니다.", payload);
            });
    }
}
