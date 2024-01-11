package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import java.io.IOException;

public class StringAreaCodeDeserializer extends JsonDeserializer<AreaCode> {

    @Override
    public AreaCode deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        return AreaCode.from(parser.getText());
    }
}
