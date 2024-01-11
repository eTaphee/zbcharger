package com.zerobase.zbcharger.domain.charger.type;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StationKindCode implements EnumCode {

    A0("A0", "공공시설"),
    B0("B0", "주차시설"),
    C0("C0", "휴게시설"),
    D0("D0", "관광시설"),
    E0("E0", "상업시설"),
    F0("F0", "차량정비시설"),
    G0("G0", "기타시설"),
    H0("H0", "공통주택시설"),
    I0("I0", "근린생활시설"),
    J0("J0", "교육문화시설");

    private final String value;
    private final String description;
}
