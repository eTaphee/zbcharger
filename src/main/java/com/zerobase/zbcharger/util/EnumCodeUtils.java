package com.zerobase.zbcharger.util;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import java.util.EnumSet;

public final class EnumCodeUtils {

    public static <C, T extends Enum<T> & EnumCode<C>> T valueOf(Class<T> enumType, String value) {
        return EnumSet.allOf(enumType)
            .stream()
            .filter(v -> v.getValue().toString().equals(value))
            .findFirst()
            .orElse(null);
    }

    public static <C, T extends Enum<T> & EnumCode<C>> T descriptionOf(Class<T> enumType,
        String description) {
        return EnumSet.allOf(enumType)
            .stream()
            .filter(v -> v.getDescription().equals(description))
            .findFirst()
            .orElse(null);
    }
}
