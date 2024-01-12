package com.zerobase.zbcharger.domain.charger.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringAreaCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringAreaDetailCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateStationRequest(
    @Size(min = 2, max = 2) String companyId,
    @NotBlank String name,
    @NotBlank String address,
    String location,
    @NotBlank String useTime,
    @NotNull @JsonDeserialize(using = StringAreaCodeDeserializer.class) AreaCode areaCode,
    @NotNull @JsonDeserialize(using = StringAreaDetailCodeDeserializer.class) AreaDetailCode areaDetailCode,
    @NotNull StationKindCode stationKindCode,
    @NotNull StationKindDetailCode stationKindDetailCode,
    boolean parkingFreeYn,
    String note,
    boolean useLimitYn,
    String useLimitDetail,
    boolean trafficYn,
    Double longitude,
    Double latitude
) {

}
