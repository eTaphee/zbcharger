package com.zerobase.zbcharger.domain.charger.dto;

import static com.zerobase.zbcharger.domain.charger.type.ChargerStat.AVAILABLE;

import com.zerobase.zbcharger.domain.charger.entity.Charger;
import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

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
    @NotBlank String id,
    @NotBlank String stationId,
    Set<ChargerType> chargerType,
    ChargeMethod method,
    int output
) {

    public Charger toEntity() {
        return Charger.builder()
            .id(id)
            .stationId(stationId)
            .stat(AVAILABLE)
            .chargerType(chargerType)
            .method(method)
            .output(output)
            .build();
    }
}
