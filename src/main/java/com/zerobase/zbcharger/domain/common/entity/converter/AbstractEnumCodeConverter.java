package com.zerobase.zbcharger.domain.common.entity.converter;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import com.zerobase.zbcharger.util.EnumCodeUtils;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

/**
 * EnumCode Converter
 *
 * @param <C> code 타입
 * @param <T> enum 타입
 */
@RequiredArgsConstructor
public abstract class AbstractEnumCodeConverter<C, T extends Enum<T> & EnumCode<C>> implements
    AttributeConverter<T, C> {

    private final Class<T> enumType;

    @Override
    public C convertToDatabaseColumn(T attribute) {
        return (attribute != null) ? attribute.getValue() : null;
    }

    @Override
    public T convertToEntityAttribute(C dbData) {
        return EnumCodeUtils.valueOf(enumType, (dbData != null) ? dbData.toString() : null);
    }
}
