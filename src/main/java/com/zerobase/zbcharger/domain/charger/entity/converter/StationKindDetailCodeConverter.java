package com.zerobase.zbcharger.domain.charger.entity.converter;

import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import com.zerobase.zbcharger.domain.common.entity.converter.AbstractEnumCodeConverter;

public class StationKindDetailCodeConverter extends
    AbstractEnumCodeConverter<String, StationKindDetailCode> {

    public StationKindDetailCodeConverter() {
        super(StationKindDetailCode.class);
    }
}
