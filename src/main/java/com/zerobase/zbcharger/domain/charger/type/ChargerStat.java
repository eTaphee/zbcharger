package com.zerobase.zbcharger.domain.charger.type;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChargerStat implements EnumCode {
    UNKNOWN("0", "알수없음"),
    ERROR("1", "통신이상"),
    AVAILABLE("2", "충전대기"),
    CHARGING("3", "충전중"),
    UNAVAILABLE("4", "운영중지"),
    MAINTENANCE("5", "점검중"),
    UNIDENTIFIED("9", "상태미확인");

    private final String value;
    private final String description;
}
