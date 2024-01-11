package com.zerobase.zbcharger.domain.charger.entity.converter;

import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ChargerTypeConverter implements
    AttributeConverter<Set<ChargerType>, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Set<ChargerType> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return 0;
        }

        return attribute.stream()
            .map(ChargerType::getValue)
            .reduce((a, b) -> a | b)
            .orElse(0);
    }

    @Override
    public Set<ChargerType> convertToEntityAttribute(Integer dbData) {
        EnumSet<ChargerType> set = EnumSet.noneOf(ChargerType.class);

        Arrays.stream(ChargerType.values())
            .filter(type -> type.isFlagOfValue(dbData))
            .forEach(set::add);

        return Collections.unmodifiableSet(set);
    }
}
