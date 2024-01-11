package com.zerobase.zbcharger.util;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import java.util.EnumSet;

public final class EnumCodeUtils {

    public static <T extends Enum<T> & EnumCode> T valueOf(Class<T> enumType, String value) {
        return EnumSet.allOf(enumType)
            .stream()
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElse(null);
    }

    public static <T extends Enum<T> & EnumCode> T descriptionOf(Class<T> enumType,
        String description) {
        return EnumSet.allOf(enumType)
            .stream()
            .filter(v -> v.getDescription().equals(description))
            .findFirst()
            .orElse(null);
    }
}
