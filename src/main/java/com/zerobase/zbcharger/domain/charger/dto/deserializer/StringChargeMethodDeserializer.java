package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;

public class StringChargeMethodDeserializer extends JsonDeserializer<ChargeMethod> {

    @Override
    public ChargeMethod deserialize(JsonParser parser, DeserializationContext ctxt) {
        try {
            return ChargeMethod.from(parser.getText());
        } catch (Exception e) {
            return null;
        }
    }
}
