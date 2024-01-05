package com.zerobase.zbcharger.domain.payment.api.naver.dto;

/**
 * 정기/반복 결제 정보
 *
 * @param recurrentId           정기/반복 결제 등록 번호
 * @param productCode           상품 코드
 * @param naverPointUse         네이버페이 포인트 사용 여부
 * @param primaryPayMeans       결제 수단
 * @param primaryPayMeansCorpCd 결제 수단 회사 코드
 * @param primaryPayMeansNo     결제 수단 번호
 */
public record Recurrent(String recurrentId, String productCode, String naverPointUse,
                        String primaryPayMeans, String primaryPayMeansCorpCd,
                        String primaryPayMeansNo) {

}
