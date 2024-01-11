package com.zerobase.zbcharger.domain.charger.type;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChargeMethod {
    SINGLE("SINGLE", "단독"),
    MULTI("MULTI", "동시");

    private final String value;
    private final String description;

    public static ChargeMethod from(String description) {
        return Arrays.stream(ChargeMethod.values())
            .filter(v -> v.getDescription().equals(description))
            .findFirst().orElse(null);
    }
}
