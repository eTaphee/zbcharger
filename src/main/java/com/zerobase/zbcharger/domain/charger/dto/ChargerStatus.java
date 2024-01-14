package com.zerobase.zbcharger.domain.charger.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerobase.zbcharger.domain.charger.type.ChargerStat;
import java.time.LocalDateTime;

/**
 * 충전기 상태
 *
 * @param companyId            회사 아이디
 * @param stationId            충전소 아이디
 * @param chargerId            충전기 아이디
 * @param stat                 충전기 상태
 * @param statUpdatedAt        충전기 상태 업데이트 일시
 * @param lastChargeStartedAt  충전 시작 일시
 * @param lastChargeFinishedAt 충전 종료 일시
 * @param nowChargeStartedAt   현재 충전 시작 일시
 */
public record ChargerStatus(
    @JsonProperty("busiId") String companyId,
    @JsonProperty("statId") String stationId,
    @JsonProperty("chgerId") String chargerId,
    ChargerStat stat,
    @JsonProperty("statUpdDt") LocalDateTime statUpdatedAt,
    @JsonProperty("lastTsdt") LocalDateTime lastChargeStartedAt,
    @JsonProperty("lastTedt") LocalDateTime lastChargeFinishedAt,
    @JsonProperty("nowTsdt") LocalDateTime nowChargeStartedAt
) {

}
