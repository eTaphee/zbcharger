package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import java.io.IOException;

public class StringAreaDetailCodeDeserializer extends JsonDeserializer<AreaDetailCode> {

    @Override
    public AreaDetailCode deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException {
        return AreaDetailCode.from(parser.getText());
    }
}
