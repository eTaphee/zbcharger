package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class StringBooleanDeserializer extends JsonDeserializer<Boolean> {

    private final static String YES = "Y";

    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
        return YES.equalsIgnoreCase(parser.getText());
    }
}
