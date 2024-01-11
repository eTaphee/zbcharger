package com.zerobase.zbcharger.domain.charger.entity.converter;

import com.zerobase.zbcharger.domain.charger.type.ChargerStat;
import com.zerobase.zbcharger.domain.common.entity.converter.AbstractEnumCodeConverter;

public class ChargerStatConverter extends AbstractEnumCodeConverter<String, ChargerStat> {

    public ChargerStatConverter() {
        super(ChargerStat.class);
    }
}
