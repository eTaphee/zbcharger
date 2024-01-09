package com.zerobase.zbcharger.domain.charger.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateStationRequest(
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

}
