package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.common.deserializer.AbstractEnumCodeDeserializer;

public class StringAreaDetailCodeDeserializer extends AbstractEnumCodeDeserializer<AreaDetailCode> {

    public StringAreaDetailCodeDeserializer() {
        super(AreaDetailCode.class, null);
    }
}
