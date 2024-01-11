package com.zerobase.zbcharger.domain.charger.entity.converter;

import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.common.entity.converter.AbstractEnumCodeConverter;

public class StationKindCodeConverter extends AbstractEnumCodeConverter<String, StationKindCode> {

    public StationKindCodeConverter() {
        super(StationKindCode.class);
    }
}
