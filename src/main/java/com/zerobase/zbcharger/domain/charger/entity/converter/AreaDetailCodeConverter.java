package com.zerobase.zbcharger.domain.charger.entity.converter;

import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.common.entity.converter.AbstractEnumCodeConverter;

public class AreaDetailCodeConverter extends AbstractEnumCodeConverter<String, AreaDetailCode> {

    public AreaDetailCodeConverter() {
        super(AreaDetailCode.class);
    }
}
