package com.zerobase.zbcharger.domain.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.type.TypeParser;
import com.zerobase.zbcharger.domain.common.type.EnumCode;
import com.zerobase.zbcharger.util.EnumCodeUtils;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractEnumCodeDeserializer<C, T extends Enum<T> & EnumCode<C>> extends
    JsonDeserializer<T> {

    private final Class<T> enumType;
    private final T defaultValue;

    @Override
    public T deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException {
        T code = EnumCodeUtils.valueOf(enumType, parser.getText());
        return (code == null) ? defaultValue : code;
    }
}
