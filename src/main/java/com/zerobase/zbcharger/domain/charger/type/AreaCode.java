package com.zerobase.zbcharger.domain.charger.type;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AreaCode implements EnumCode<String> {
    Z11("11", "서울특별시"),
    Z26("26", "부산광역시"),
    Z27("27", "대구광역시"),
    Z28("28", "인천광역시"),
    Z29("29", "광주광역시"),
    Z30("30", "대전광역시"),
    Z31("31", "울산광역시"),
    Z36("36", "세종특별자치시"),
    Z41("41", "경기도"),
    Z42("42", "강원도"),
    Z43("43", "충청북도"),
    Z44("44", "충청남도"),
    Z45("45", "전라북도"),
    Z46("46", "전라남도"),
    Z47("47", "경상북도"),
    Z48("48", "경상남도"),
    Z50("50", "제주특별자치도"),
    Z51("51", "강원특별자치도");

    private final String value;
    private final String description;
}
