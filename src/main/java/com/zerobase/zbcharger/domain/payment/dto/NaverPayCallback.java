package com.zerobase.zbcharger.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 네이버페이 결제 콜백 DTO
 */
@Getter
public class NaverPayCallback extends RegisterPaymentCallback {

    private final String resultCode;
    private final String resultMessage;
    private final String reserveId;
    private final String tempReceiptId;
    private final String recurrentId;
    private final String userEmail;

    @Builder
    public NaverPayCallback(Long memberId, String resultCode, String resultMessage,
        String reserveId, String tempReceiptId, String recurrentId, String userEmail) {
        super(memberId);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.reserveId = reserveId;
        this.tempReceiptId = tempReceiptId;
        this.recurrentId = recurrentId;
        this.userEmail = userEmail;
    }
}
