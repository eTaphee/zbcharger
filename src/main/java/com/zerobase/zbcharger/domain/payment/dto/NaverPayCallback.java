package com.zerobase.zbcharger.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NaverPayCallback extends RegisterPaymentCallback {

    private final String resultCode;
    private final String resultMessage;
    private final String reserveId;
    private final String tempReceiptId;
    private final String recurrentId;
    private final String userEmail;

    @Builder
    public NaverPayCallback(String resultCode, String resultMessage, String reserveId,
        String tempReceiptId, String recurrentId, String userEmail) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.reserveId = reserveId;
        this.tempReceiptId = tempReceiptId;
        this.recurrentId = recurrentId;
        this.userEmail = userEmail;
    }
}
