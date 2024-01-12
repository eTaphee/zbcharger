package com.zerobase.zbcharger.domain.charger.type;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OutputType implements EnumCode<Integer> {
    SLOW(1, "완속"),
    FAST(2, "급속");

    public String getKey() {
        return name();
    }

    private final Integer value;
    private final String description;
}
