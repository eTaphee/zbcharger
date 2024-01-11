package com.zerobase.zbcharger.domain.charger.entity.converter;

import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.common.entity.converter.AbstractEnumCodeConverter;

public class ChargeMethodConverter extends AbstractEnumCodeConverter<String, ChargeMethod> {

    public ChargeMethodConverter() {
        super(ChargeMethod.class);
    }
}
