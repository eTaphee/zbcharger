package com.zerobase.zbcharger.domain.charger.entity.converter;

import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.common.entity.converter.AbstractEnumCodeConverter;

public class AreaCodeConverter extends AbstractEnumCodeConverter<String, AreaCode> {

    public AreaCodeConverter() {
        super(AreaCode.class);
    }
}
