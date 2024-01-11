package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import com.zerobase.zbcharger.domain.common.deserializer.AbstractEnumCodeDeserializer;

public class StringStationKindDetailCodeDeserializer extends
    AbstractEnumCodeDeserializer<String, StationKindDetailCode> {

    public StringStationKindDetailCodeDeserializer() {
        super(StationKindDetailCode.class, null);
    }
}
