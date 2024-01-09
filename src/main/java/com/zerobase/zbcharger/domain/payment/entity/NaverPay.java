package com.zerobase.zbcharger.domain.payment.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("NAVER_PAY")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class NaverPay extends PaymentMethod {

    /**
     * 정식 정기/반복결제 등록 번호
     */
    private final String recurrentId;

    // TODO: 상품 코드 정의, 상품 테이블 필요한가?
    /**
     * 상품 코드
     */
    private final String productCode;

    /**
     * 주 결제 수단(CARD: 신용카드, BANK: 계좌 이체)
     */
    private final String primaryPayMeans;

    // TODO: 카드사/은행 코드 공통 코드로 정의 필요(https://developer.pay.naver.com/docs/v2/api#etc-etc_card)
    /**
     * 주 결제 수단 카드사/은행 코드
     */
    private final String primaryPayMeansCorpCd;

    /**
     * 주 결제 수단 번호. 일부 마스킹된 카드/계좌 번호
     */
    private final String primaryPayMeansNo;

    @Builder
    public NaverPay(Long memberId, String recurrentId, String productCode, String primaryPayMeans,
        String primaryPayMeansCorpCd, String primaryPayMeansNo) {
        super(memberId, PayMethod.NAVER_PAY);
        this.recurrentId = recurrentId;
        this.productCode = productCode;
        this.primaryPayMeans = primaryPayMeans;
        this.primaryPayMeansCorpCd = primaryPayMeansCorpCd;
        this.primaryPayMeansNo = primaryPayMeansNo;
    }
}
