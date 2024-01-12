package com.zerobase.zbcharger.domain.charger.dao.custom.impl;

import static com.zerobase.zbcharger.domain.charger.entity.QCharger.charger;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.zbcharger.domain.charger.dao.custom.CustomChargerRepository;
import com.zerobase.zbcharger.domain.charger.dto.ChargerSummary;
import com.zerobase.zbcharger.domain.charger.dto.SearchChargerSummaryCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomChargerRepositoryImpl implements CustomChargerRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChargerSummary> findAllChargeSummary(SearchChargerSummaryCondition condition) {
        return queryFactory
            .select(
                Projections.constructor(ChargerSummary.class,
                    charger.id,
                    charger.chargerType,
                    charger.output,
                    charger.stat,
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
}
