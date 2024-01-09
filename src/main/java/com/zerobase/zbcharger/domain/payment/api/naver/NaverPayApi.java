package com.zerobase.zbcharger.domain.payment.api.naver;

import com.zerobase.zbcharger.domain.payment.api.naver.dto.ApprovalRecurrentRegistrationResponseBody;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.NaverPayResponse;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.SearchRegisteredRecurrentRequest;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.SearchRegisteredRecurrentResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.PostExchange;

/**
 * 네이버페이 API proxy 인터페이스
 */
public interface NaverPayApi {

    /**
     * 정기/반복결제 등록 완료
     *
     * @param clientId       클라이언트 아이디
     * @param clientSecret   클라이언트 시크릿
     * @param chainId        체인 아이디
     * @param idempotencyKey API 멱등성 키
     * @param reserveId      등록 예약 번호
     * @param tempReceiptId  임시 접수 번호
     */
    @PostExchange("/naverpay-partner/naverpay/payments/recurrent/regist/v1/approval")
    NaverPayResponse<ApprovalRecurrentRegistrationResponseBody> approvalRecurrentRegistration(
        @RequestHeader("X-Naver-Client-Id") String clientId,
        @RequestHeader("X-Naver-Client_Secret") String clientSecret,
        @RequestHeader("X-NaverPay-Chain-Id") String chainId,
        @RequestHeader("X-NaverPay-Idempotency-Key") String idempotencyKey,
        @RequestBody String reserveId,
        @RequestBody String tempReceiptId);

    /**
     * 등록된 정기/반복결제 조회
     *
     * @param clientId     클라이언트 아이디
     * @param clientSecret 클라이언트 시크릿
     * @param chainId      체인 아이디
     * @param request      요청
     */
    @PostExchange("/naverpay-partner/naverpay/payments/recurrent/v1/list")
    NaverPayResponse<SearchRegisteredRecurrentResponseBody> getRegisteredRecurrentList(
        @RequestHeader("X-Naver-Client-Id") String clientId,
        @RequestHeader("X-Naver-Client_Secret") String clientSecret,
        @RequestHeader("X-NaverPay-Chain-Id") String chainId,
        @RequestBody SearchRegisteredRecurrentRequest request);
}
