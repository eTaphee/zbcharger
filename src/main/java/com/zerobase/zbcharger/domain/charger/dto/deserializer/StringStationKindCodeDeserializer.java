package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.common.deserializer.AbstractEnumCodeDeserializer;

public class StringStationKindCodeDeserializer extends
    AbstractEnumCodeDeserializer<String, StationKindCode> {

    public StringStationKindCodeDeserializer() {
        super(StationKindCode.class, null);
    }
}
