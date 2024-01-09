package com.zerobase.zbcharger.domain.charger.dto.admin;

public record SearchStationRequest(
    String companyId,
    String areaCode,
    String areaDetailCode,
    String stationKindCode,
    String stationKindDetailCode,
    Boolean parkingFreeYn,
    Boolean useLimitYn,
    Boolean trafficYn,
    Boolean deletedYn
) {

}
