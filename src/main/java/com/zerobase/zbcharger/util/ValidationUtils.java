package com.zerobase.zbcharger.util;

import org.springframework.util.StringUtils;

public final class ValidationUtils {

    public static void StringMustBeNotBlank(String field, String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(field + " is required");
        }
    }
}
