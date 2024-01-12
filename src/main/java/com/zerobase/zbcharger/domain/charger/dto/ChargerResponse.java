package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.entity.Charger;
import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.charger.type.ChargerStat;
import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;

@Builder
public record ChargerResponse(
    String id,
    String stationId,
    Set<ChargerType> chargerTypes,
    String location,
    ChargerStat stat,
    int output,
    ChargeMethod method,
    LocalDateTime statUpdatedAt,
    LocalDateTime lastChargeStartedAt,
    LocalDateTime lastChargeEndedAt,
    LocalDateTime nowChargeStartedAt,
    boolean deletedYn,
    String deleteDetail
) {

    public static ChargerResponse fromEntity(Charger charger) {
        return ChargerResponse.builder()
            .id(charger.getId())
            .stationId(charger.getStationId())
            .chargerTypes(charger.getChargerType())
            .stat(charger.getStat())
            .output(charger.getOutput())
            .method(charger.getMethod())
            .statUpdatedAt(charger.getStatUpdatedAt())
            .lastChargeStartedAt(charger.getLastChargeStartedAt())
            .lastChargeEndedAt(charger.getLastChargeFinishedAt())
            .nowChargeStartedAt(charger.getNowChargeStartedAt())
            .deletedYn(charger.isDeletedYn())
            .deleteDetail(charger.getDeleteDetail())
            .build();
    }
}
