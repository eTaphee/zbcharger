package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.common.deserializer.AbstractEnumCodeDeserializer;

public class StringAreaCodeDeserializer extends AbstractEnumCodeDeserializer<AreaCode> {

    public StringAreaCodeDeserializer() {
        super(AreaCode.class, null);
    }
}
