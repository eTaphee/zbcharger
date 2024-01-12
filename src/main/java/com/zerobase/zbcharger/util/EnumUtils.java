package com.zerobase.zbcharger.util;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public final class EnumUtils {

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

    /**
     * 비트 플래그 값에 포함되어 있는지 확인
     *
     * @param value 비트 플래그 값
     * @return 포함 여부
     */
    public static <T extends EnumCode<Integer>> boolean isFlagOfValue(T enumValue, int value) {
        Integer enumValueInt = enumValue.getValue();
        if (enumValueInt == 0) {
            return false;
        }

        return (value & enumValueInt) == enumValueInt;
    }

    public static <T extends Enum<T> & EnumCode<Integer>> Set<T> getFlagSet(Class<T> enumType,
        int value) {
        EnumSet<T> set = EnumSet.noneOf(enumType);

        EnumSet.allOf(enumType)
            .stream()
            .filter(m -> EnumUtils.isFlagOfValue(m, value))
            .forEach(set::add);

        return Collections.unmodifiableSet(set);
    }
}
