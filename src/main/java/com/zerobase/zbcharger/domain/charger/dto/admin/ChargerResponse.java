package com.zerobase.zbcharger.domain.charger.dto.admin;

import com.zerobase.zbcharger.domain.charger.entity.Charger;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChargerResponse(
    String id,
    String stationId,
    String chargerType,
    String location,
    int stat,
    String output,
    String method,
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
            .chargerType(charger.getChargerType())
            .location(charger.getLocation())
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
