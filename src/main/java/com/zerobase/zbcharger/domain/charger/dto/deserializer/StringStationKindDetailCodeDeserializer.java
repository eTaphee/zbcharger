package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;

public class StringStationKindDetailCodeDeserializer extends
    JsonDeserializer<StationKindDetailCode> {

    @Override
    public StationKindDetailCode deserialize(JsonParser parser, DeserializationContext ctxt) {
        try {
            return StationKindDetailCode.valueOf(parser.getText());
        } catch (Exception e) {
            return null;
        }
    }
}
