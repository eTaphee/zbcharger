package com.zerobase.zbcharger.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("CARD")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class SmartroPay extends PaymentMethod {

    /**
     * 주문 번호
     */
    private final String orderId;

    /**
     * 거래 아이디
     */
    private final String transactionId;

    /**
     * 빌링키
     */
    private final String billTokenKey;

    /**
     * 카드사 코드
     */
    @Column(columnDefinition = "char(2)")
    private final String issuerCardCode;

    /**
     * 카드사 이름
     */
    private final String issuerCardName;

    /**
     * 표시용 카드 번호
     */
    private final String displayCardNo;

    /**
     * 빌링키 유효 기간
     */
    private final LocalDateTime cardExpire;

    @Builder
    public SmartroPay(Long memberId, String orderId, String transactionId,
        String billTokenKey, String issuerCardCode,
        String issuerCardName, String displayCardNo, LocalDateTime cardExpire) {
        super(memberId, PayMethod.CARD);
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.billTokenKey = billTokenKey;
        this.issuerCardCode = issuerCardCode;
        this.issuerCardName = issuerCardName;
        this.displayCardNo = displayCardNo;
        this.cardExpire = cardExpire;
    }
}
