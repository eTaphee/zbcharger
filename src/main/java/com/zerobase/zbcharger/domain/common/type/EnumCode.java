package com.zerobase.zbcharger.domain.common.type;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface EnumCode<T> {

    T getValue();

    String getDescription();
}
