package com.zerobase.zbcharger.domain.charger.type;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChargeMethod implements EnumCode {
    SINGLE("S", "단독"),
    MULTI("M", "동시");

    private final String value;
    private final String description;
}
