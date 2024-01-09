package com.zerobase.zbcharger.domain.charger.dto.admin;

import com.zerobase.zbcharger.domain.charger.entity.Station;
import com.zerobase.zbcharger.domain.charger.entity.StationKind;
import com.zerobase.zbcharger.domain.charger.entity.StationKindDetail;
import com.zerobase.zbcharger.domain.charger.entity.ZCode;
import com.zerobase.zbcharger.domain.charger.entity.ZSCode;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record StationResponse(
    String id,
    String companyId,
    String name,
    String address,
    String useTime,
    String area,
    String areaDetail,
    String stationKind,
    String stationKindDetail,
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
            .useTime(station.getUseTime())
            .area(ZCode.from(station.getAreaCode()).getName())
            .areaDetail(ZSCode.from(station.getAreaDetailCode()).getName())
            .stationKind(StationKind.valueOf(station.getStationKindCode()).getName())
            .stationKindDetail(
                StationKindDetail.valueOf(station.getStationKindDetailCode()).getName())
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
