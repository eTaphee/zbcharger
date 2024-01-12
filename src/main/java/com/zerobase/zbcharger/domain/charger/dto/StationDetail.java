package com.zerobase.zbcharger.domain.charger.dto;

/**
 * 충전소 상세 정보
 *
 * @param address       주소
 * @param location      상세위치
 * @param operator      운영기관
 * @param operatorTel   연락처
 * @param useTime       운영시간
 * @param parkingFreeYn 주차비
 * @param useLimitYn    이용정보
 */
public record StationDetail(
    String address,
    String location,
    String operator,
    String operatorTel,
    String useTime,
    boolean parkingFreeYn,
    boolean useLimitYn
) {

}
