package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.entity.Station;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 충전소 정보
 */
@EqualsAndHashCode(of = "id")
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StationInfo {

    /**
     * 충전소 아이디
     */
    private final String id;

    /**
     * 회사 아이디
     */
    private final String companyId;

    /**
     * 충전소 일므
     */
    private final String name;

    /**
     * 주소
     */
    private final String address;

    /**
     * 위치
     */
    private final String location;

    /**
     * 이용 가능 시간
     */
    private final String useTime;

    /**
     * 지역 코드
     */
    private final AreaCode areaCode;

    /**
     * 지역구분 상세 코드
     */
    private final AreaDetailCode areaDetailCode;

    /**
     * 충전소 구분 코드
     */
    private final StationKindCode stationKindCode;

    /**
     * 충전소 구분 상세 코드
     */
    private final StationKindDetailCode stationKindDetailCode;

    /**
     * 주차료 무료
     */
    private final boolean parkingFreeYn;

    /**
     * 안내
     */
    private final String note;

    /**
     * 이용자 제한
     */
    private final boolean limitYn;

    /**
     * 이용제한 사유
     */
    private final String limitDetail;

    /**
     * 편의제공 여부
     */
    private final boolean trafficYn;

    /**
     * 위도
     */
    private final Double latitude;

    /**
     * 경도
     */
    private final Double longitude;

    public Station toEntity() {
        return Station.builder()
            .id(id)
            .companyId(companyId)
            .name(name)
            .address(address)
            .location(location)
            .useTime(useTime)
            .areaCode(areaCode)
            .areaDetailCode(areaDetailCode)
            .stationKindCode(stationKindCode)
            .stationKindDetailCode(stationKindDetailCode)
            .parkingFreeYn(parkingFreeYn)
            .note(note)
            .useLimitYn(limitYn)
            .trafficYn(trafficYn)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
