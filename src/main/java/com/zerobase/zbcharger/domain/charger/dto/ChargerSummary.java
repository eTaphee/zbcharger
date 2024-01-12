package com.zerobase.zbcharger.domain.charger.dto;

import com.zerobase.zbcharger.domain.charger.type.ChargerStat;
import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 충전기 요약 정보
 *
 * @param id                   충전기 아이디
 * @param chargerTypes         충전기 타입
 * @param stat                 충전기 상태
 * @param output               충전기 출력
 * @param statUpdatedAt        충전기 상태 업데이트 시간
 * @param lastChargeStartedAt  마지막 충전 시작 시간
 * @param lastChargeFinishedAt 마지막 충전 종료 시간
 * @param nowChargeStartedAt   현재 충전 시작 시간
 */
public record ChargerSummary(
    String id,
    Set<ChargerType> chargerTypes,
    ChargerStat stat,
    int output,
    LocalDateTime statUpdatedAt,
    LocalDateTime lastChargeStartedAt,
    LocalDateTime lastChargeFinishedAt,
    LocalDateTime nowChargeStartedAt
) {

}
