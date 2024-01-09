package com.zerobase.zbcharger.domain.payment.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * 스마트로페이 결제 콜백 DTO
 */
@Getter
public class SmartroPayCallback extends RegisterPaymentCallback {

    private final String payMethod;
    private final String merchantId;
    private final String orderId;
    private final String transactionId;
    private final String billTokenKey;
    private final String resultCode;
    private final String resultMessage;
    private final String verifyValue;
    private final String issuerCardCode;
    private final String issuerCardName;
    private final String displayCardNumber;
    private final LocalDateTime cardExpire;
    private final String encodingType;
    private final boolean returnUrlEncodingUse;

    @Builder
    private SmartroPayCallback(String payMethod, String merchantId, String orderId,
        String transactionId, String billTokenKey, String mallUserId, String resultCode,
        String resultMessage, String verifyValue, String issuerCardCode, String issuerCardName,
        String displayCardNumber, LocalDateTime cardExpire, String encodingType,
        boolean returnUrlEncodingUse) {
        super(Long.parseLong(mallUserId));
        this.payMethod = payMethod;
        this.merchantId = merchantId;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.billTokenKey = billTokenKey;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.verifyValue = verifyValue;
        this.issuerCardCode = issuerCardCode;
        this.issuerCardName = issuerCardName;
        this.displayCardNumber = displayCardNumber;
        this.cardExpire = cardExpire;
        this.encodingType = encodingType;
        this.returnUrlEncodingUse = returnUrlEncodingUse;
    }
}
