package com.zerobase.zbcharger.domain.payment.api.naver.impl;

import static com.zerobase.zbcharger.domain.payment.api.naver.dto.constant.NaverPayApiResponseCode.SUCCESS;

import com.zerobase.zbcharger.domain.payment.api.naver.NaverPayApi;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.ApprovalRecurrentRegistrationResponseBody;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.NaverPayResponse;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.Recurrent;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.SearchRegisteredRecurrentRequest;
import com.zerobase.zbcharger.domain.payment.api.naver.dto.SearchRegisteredRecurrentResponseBody;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * 네이버페이 API Mock
 */
@Component
public class MockNaverPayApiImpl implements NaverPayApi {

    @Override
    public NaverPayResponse<ApprovalRecurrentRegistrationResponseBody> approvalRecurrentRegistration(
        String clientId, String clientSecret, String chainId, String idempotencyKey,
        String reserveId, String tempReceiptId) {
        return new NaverPayResponse<>(SUCCESS.getValue(), null,
            new ApprovalRecurrentRegistrationResponseBody(
                reserveId,
                tempReceiptId,
                UUID.randomUUID().toString(),
                "ACTION",
                null));
    }

    @Override
    public NaverPayResponse<SearchRegisteredRecurrentResponseBody> getRegisteredRecurrentList(
        String clientId, String clientSecret, String chainId,
        SearchRegisteredRecurrentRequest request) {
        return new NaverPayResponse<>(SUCCESS.getValue(), null,
            new SearchRegisteredRecurrentResponseBody(new Recurrent[]{
                new Recurrent(
                    request.recurrentId(),
                    "PRODUCT_CODE",
                    "Y",
                    "CARD",
                    "11",
                    "5239-****-****-9001"
                )
            }, 1, 1, 1, 1));
    }
}
