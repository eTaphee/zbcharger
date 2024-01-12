package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;

public record SearchStationCondition(
    String companyId,
    AreaCode areaCode,
    AreaDetailCode areaDetailCode,
    StationKindCode stationKindCode,
    StationKindDetailCode stationKindDetailCode,
    Boolean parkingFreeYn,
    Boolean useLimitYn,
    Boolean trafficYn,
    Boolean deletedYn
) {

}
