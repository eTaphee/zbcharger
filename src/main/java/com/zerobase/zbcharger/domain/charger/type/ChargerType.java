package com.zerobase.zbcharger.domain.charger.type;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 충전기 타입
 */
@Getter
@RequiredArgsConstructor
public enum ChargerType implements EnumCode<Integer> {
    NONE(0, "알수없음"),
    AC(1, "AC"),
    AC3(2, "AC3"),
    CHA(4, "차데모"),
    DC(8, "DC");

    public String getKey() {
        return name();
    }

    private final Integer value;
    private final String description;
}
