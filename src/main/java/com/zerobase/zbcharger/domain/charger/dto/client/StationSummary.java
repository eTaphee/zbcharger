package com.zerobase.zbcharger.domain.charger.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 충전소 요약 정보
 *
 * @param company       충전소 회사 정보
 * @param id            충전소 아이디
 * @param name          충전소 이름
 * @param address       충전소 주소
 * @param location      충전소 위치
 * @param latitude      위도
 * @param longitude     경도
 * @param distance      현재 위치와의 거리
 * @param useLimitYn    사용 제한 여부
 * @param parkingFreeYn 주차 무료 여부
 * @param stationKind   충전소 종류
 * @param chargerType   충전기 타입 코드
 */
public record StationSummary(
    Company company,
    String id,
    String name,
    String address,
    String location,
    double latitude,
    double longitude,
    double distance,
    boolean useLimitYn,
    boolean parkingFreeYn,
    StationKindCode stationKind,
    @JsonIgnore
    int chargerType
) {

    @JsonProperty("chargerTypes")
    public Set<ChargerType> getChargerTypes() {
        EnumSet<ChargerType> set = EnumSet.noneOf(ChargerType.class);

        Arrays.stream(ChargerType.values())
            .filter(m -> m.isFlagOfValue(chargerType))
            .forEach(set::add);

        return Collections.unmodifiableSet(set);
    }

    public record Company(String id, String name) {

    }
}
