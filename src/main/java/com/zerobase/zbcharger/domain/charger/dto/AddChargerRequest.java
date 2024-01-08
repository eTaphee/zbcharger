package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.entity.Charger;

/**
 * 충전기 추가 요청
 *
 * @param id          충전기 아이디
 * @param stationId   충전소 아이디
 * @param chargerType 충전기 타입
 * @param location    위치
 * @param output      충전 용량
 * @param method      충전 방식
 */
public record AddChargerRequest(
    String id,
    String stationId,
    String chargerType,
    String location,
    String output,
    String method
) {

    public Charger toEntity() {
        return Charger.builder()
            .id(id)
            .stationId(stationId)
            .chargerType(chargerType)
            .location(location)
            .output(output)
            .method(method)
            .build();
    }
}
