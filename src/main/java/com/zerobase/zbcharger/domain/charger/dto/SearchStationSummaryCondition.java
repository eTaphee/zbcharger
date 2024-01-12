package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import java.util.Set;

/**
 * 충전소 요약 검색 조건
 *
 * @param companyIds       충전소 아이디 목록
 * @param chargerTypes     충전기 타입 코드 목록
 * @param parkingFreeYn    주차 무료 여부
 * @param useLimitYn       사용 제한 여부
 * @param currentLatitude  현재 위도(사용자 위치)
 * @param currentLongitude 현재 경도(사용자 위치)
 * @param baseLatitude     기준 위도
 * @param baseLongitude    기준 경도
 * @param radius           조회 반경
 */
public record SearchStationSummaryCondition(
    Set<String> companyIds,
    Set<ChargerType> chargerTypes,
    Boolean parkingFreeYn,
    Boolean useLimitYn,
    double currentLatitude,
    double currentLongitude,
    double baseLatitude,
    double baseLongitude,
    double radius
) {

}
