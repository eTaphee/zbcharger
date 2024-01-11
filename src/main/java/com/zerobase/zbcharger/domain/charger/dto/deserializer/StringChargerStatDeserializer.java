package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.zerobase.zbcharger.domain.charger.type.ChargerStat;
import com.zerobase.zbcharger.domain.common.deserializer.AbstractEnumCodeDeserializer;

public class StringChargerStatDeserializer extends
    AbstractEnumCodeDeserializer<String, ChargerStat> {

    public StringChargerStatDeserializer() {
        super(ChargerStat.class, ChargerStat.UNKNOWN);
    }
}
