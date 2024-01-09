package com.zerobase.zbcharger.domain.charger.dto.admin;

import com.zerobase.zbcharger.domain.charger.entity.Station;
import lombok.Builder;

@Builder
public record StationResponse(
    String id,
    String companyId,
    String name,
    String address,
    String useTime,
    String areaCode,
    String areaDetailCode,
    String stationKindCode,
    String stationKindDetailCode,
    boolean parkingFreeYn,
    String note,
    boolean useLimitYn,
    String useLimitDetail,
    boolean trafficYn,
    Double longitude,
    Double latitude
) {

    public static StationResponse fromEntity(Station station) {
        return StationResponse.builder()
            .id(station.getId())
            .companyId(station.getCompanyId())
            .name(station.getName())
            .address(station.getAddress())
            .useTime(station.getUseTime())
            .areaCode(station.getAreaCode())
            .areaDetailCode(station.getAreaDetailCode())
            .stationKindCode(station.getStationKindCode())
            .stationKindDetailCode(station.getStationKindDetailCode())
            .parkingFreeYn(station.isParkingFreeYn())
            .note(station.getNote())
            .useLimitYn(station.isUseLimitYn())
            .useLimitDetail(station.getUseLimitDetail())
            .trafficYn(station.isTrafficYn())
            .latitude(station.getLatitude())
            .longitude(station.getLongitude())
            .build();

    }
}
