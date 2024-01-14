package com.zerobase.zbcharger.domain.charger.dao.custom.impl;

import static com.zerobase.zbcharger.domain.charger.entity.QCharger.charger;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.zbcharger.domain.charger.dao.custom.CustomChargerRepository;
import com.zerobase.zbcharger.domain.charger.dto.ChargerStatus;
import com.zerobase.zbcharger.domain.charger.dto.ChargerSummary;
import com.zerobase.zbcharger.domain.charger.dto.SearchChargerSummaryCondition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomChargerRepositoryImpl implements CustomChargerRepository {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ChargerSummary> findAllChargeSummary(SearchChargerSummaryCondition condition) {
        return queryFactory
            .select(
                Projections.constructor(ChargerSummary.class,
                    charger.id,
                    charger.chargerType,
                    charger.stat,
                    charger.output,
                    charger.statUpdatedAt,
                    charger.lastChargeStartedAt,
                    charger.lastChargeFinishedAt,
                    charger.nowChargeStartedAt))
            .from(charger)
            .where(charger.stationId.eq(condition.stationId()),
                charger.deletedYn.eq(false))
            .orderBy(charger.id.asc())
            .fetch();
    }

    @Override
    public void bulkUpdateChargerStatus(List<ChargerStatus> chargerStatuses) {
        LocalDateTime now = LocalDateTime.now();

        chargerStatuses.forEach(chargerStatus -> {
            String chargerId = chargerStatus.stationId() + chargerStatus.chargerId();
            queryFactory
                .update(charger)
                .set(charger.stat, chargerStatus.stat())
                .set(charger.statUpdatedAt, chargerStatus.statUpdatedAt())
                .set(charger.lastChargeStartedAt, chargerStatus.lastChargeStartedAt())
                .set(charger.lastChargeFinishedAt, chargerStatus.lastChargeFinishedAt())
                .set(charger.nowChargeStartedAt, chargerStatus.nowChargeStartedAt())
                .set(charger.updatedAt, now)
                .where(charger.id.eq(chargerId))
                .execute();
        });

        entityManager.flush();
        entityManager.clear();
    }
}
