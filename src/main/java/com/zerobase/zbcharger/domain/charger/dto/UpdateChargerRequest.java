package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import java.util.Set;

/**
 * 충전기 수정 요청
 *
 * @param chargerType 충전기 타입
 * @param method      충전 방식
 * @param output      충전 용량
 */
public record UpdateChargerRequest(
    Set<ChargerType> chargerType,
    ChargeMethod method,
    int output
) {

}
