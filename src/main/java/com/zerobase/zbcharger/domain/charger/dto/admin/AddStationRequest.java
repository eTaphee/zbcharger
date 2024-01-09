package com.zerobase.zbcharger.domain.charger.dto.admin;

import com.zerobase.zbcharger.domain.charger.entity.Station;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddStationRequest(
    @Size(min = 8, max = 8) String id,
    @Size(min = 2, max = 2) String companyId,
    @NotBlank String name,
    @NotBlank String address,
    @NotBlank String useTime,
    @Size(min = 2, max = 2) String areaCode,
    @Size(min = 5, max = 5) String areaDetailCode,
    @Size(min = 2, max = 2) String stationKindCode,
    @Size(min = 4, max = 4) String stationKindDetailCode,
    boolean parkingFreeYn,
    String note,
    boolean useLimitYn,
    String useLimitDetail,
    boolean trafficYn,
    Double longitude,
    Double latitude
) {

    public Station toEntity() {
        return Station.builder()
            .id(id)
            .companyId(companyId)
            .name(name)
            .address(address)
            .useTime(useTime)
            .areaCode(areaCode)
            .areaDetailCode(areaDetailCode)
            .stationKindCode(stationKindCode)
            .stationKindDetailCode(stationKindDetailCode)
            .parkingFreeYn(parkingFreeYn)
            .note(note)
            .useLimitYn(useLimitYn)
            .useLimitDetail(useLimitDetail)
            .trafficYn(trafficYn)
            .longitude(longitude)
            .latitude(latitude)
            .build();
    }
}
