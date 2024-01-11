package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;

public class StringStationKindCodeDeserializer extends JsonDeserializer<StationKindCode> {

    @Override
    public StationKindCode deserialize(JsonParser parser, DeserializationContext ctxt) {
        try {
            return StationKindCode.valueOf(parser.getText());
        } catch (Exception e) {
            return null;
        }
    }
}
