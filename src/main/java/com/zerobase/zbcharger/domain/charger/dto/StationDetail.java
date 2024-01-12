package com.zerobase.zbcharger.domain.charger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerobase.zbcharger.domain.charger.type.OutputType;
import com.zerobase.zbcharger.util.EnumUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * 충전소 상세 정보
 *
 * @param address       주소
 * @param location      상세위치
 * @param operator      운영기관
 * @param operatorTel   연락처
 * @param useTime       운영시간
 * @param outputType    충전기 타입
 * @param parkingFreeYn 주차비
 * @param useLimitYn    이용정보
 */
public record StationDetail(
    String address,
    String location,
    String operator,
    String operatorTel,
    String useTime,
    @JsonIgnore
    int outputType,
    boolean parkingFreeYn,
    boolean useLimitYn
) {

    @JsonProperty("outputTypes")
    public Set<OutputType> getOutputTypes() {
        return EnumUtils.getFlagSet(OutputType.class, outputType);
    }
}
