package com.zerobase.zbcharger.domain.charger.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ZCode {
    Z00("UNKNOWN"),
    Z11("서울특별시"),
    Z26("부산광역시"),
    Z27("대구광역시"),
    Z28("인천광역시"),
    Z29("광주광역시"),
    Z30("대전광역시"),
    Z31("울산광역시"),
    Z36("세종특별자치시"),
    Z41("경기도"),
    Z42("강원도"),
    Z43("충청북도"),
    Z44("충청남도"),
    Z45("전라북도"),
    Z46("전라남도"),
    Z47("경상북도"),
    Z48("경상남도"),
    Z50("제주특별자치도"),
    Z51("강원특별자치도");

    private final String name;

    public static ZCode from(String code) {
        try {
            return ZCode.valueOf("Z" + code);
        } catch (Exception e) {
            return Z00;
        }
    }
}
