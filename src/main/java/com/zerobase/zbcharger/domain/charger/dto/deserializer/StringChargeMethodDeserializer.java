package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.common.deserializer.AbstractEnumCodeDeserializer;
import com.zerobase.zbcharger.util.EnumCodeUtils;
import java.io.IOException;

public class StringChargeMethodDeserializer extends AbstractEnumCodeDeserializer<ChargeMethod> {

    public StringChargeMethodDeserializer() {
        super(ChargeMethod.class, null);
    }

    @Override
    public ChargeMethod deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException {
        return EnumCodeUtils.descriptionOf(ChargeMethod.class, parser.getText());
    }
}
