package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.entity.Station;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record StationResponse(
    String id,
    String companyId,
    String name,
    String address,
    String location,
    String useTime,
    AreaCode area,
    AreaDetailCode areaDetail,
    StationKindCode stationKind,
    StationKindDetailCode stationKindDetail,
    boolean parkingFreeYn,
    String note,
    boolean useLimitYn,
    String useLimitDetail,
    boolean trafficYn,
    Double longitude,
    Double latitude,
    boolean deletedYn,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

    public static StationResponse fromEntity(Station station) {
        return StationResponse.builder()
            .id(station.getId())
            .companyId(station.getCompanyId())
            .name(station.getName())
            .address(station.getAddress())
            .location(station.getLocation())
            .useTime(station.getUseTime())
            .area(station.getAreaCode())
            .area(station.getAreaCode())
            .areaDetail(station.getAreaDetailCode())
            .stationKind(station.getStationKindCode())
            .stationKindDetail(station.getStationKindDetailCode())
            .parkingFreeYn(station.isParkingFreeYn())
            .note(station.getNote())
            .useLimitYn(station.isUseLimitYn())
            .useLimitDetail(station.getUseLimitDetail())
            .trafficYn(station.isTrafficYn())
            .latitude(station.getLatitude())
            .longitude(station.getLongitude())
            .deletedYn(station.isDeleted())
            .createdAt(station.getCreatedAt())
            .updatedAt(station.getUpdatedAt())
            .deletedAt(station.getDeletedAt())
            .build();
    }
}
