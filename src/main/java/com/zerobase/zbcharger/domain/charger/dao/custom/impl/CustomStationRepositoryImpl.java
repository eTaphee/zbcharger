package com.zerobase.zbcharger.domain.charger.dao.custom.impl;

import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.areaCode;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.areaDetailCode;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.chargerTypeBitAnd;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.companyId;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.companyIdIn;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.deletedYn;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.distance;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.parkingFreeYn;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.stationKindCode;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.stationKindDetailCode;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.trafficYn;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.useLimitYn;
import static com.zerobase.zbcharger.domain.charger.entity.QCompany.company;
import static com.zerobase.zbcharger.domain.charger.entity.QStation.station;
import static com.zerobase.zbcharger.domain.charger.entity.QStationSummary.stationSummary;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.zbcharger.domain.charger.dao.custom.CustomStationRepository;
import com.zerobase.zbcharger.domain.charger.dto.SearchStationCondition;
import com.zerobase.zbcharger.domain.charger.dto.StationDetail;
import com.zerobase.zbcharger.domain.charger.dto.SearchStationSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.StationSummary;
import com.zerobase.zbcharger.domain.charger.entity.Station;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class CustomStationRepositoryImpl implements CustomStationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Station> findAll(Pageable pageable, SearchStationCondition request) {
        Predicate predicate = toPredicate(request);

        List<Station> contents = queryFactory.selectFrom(station)
            .where(predicate)
            .orderBy(getOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory.select(station.count())
            .from(station)
            .where(predicate)
            .fetchFirst();

        return new PageImpl<>(contents, pageable, total);
    }

    private static OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        return sort.stream()
            .map(CustomStationRepositoryImpl::applyOrder)
            .toArray(OrderSpecifier[]::new);
    }

    private static OrderSpecifier applyOrder(Sort.Order order) {
        String property = order.getProperty();

        if ("id".equals(property)) {
            if (order.isAscending()) {
                return station.id.asc();
            }
            return station.id.desc();
        }

        if ("name".equals(property)) {
            if (order.isAscending()) {
                return station.name.asc();
            }
            return station.name.desc();
        }

        if ("companyId".equals(property)) {
            if (order.isAscending()) {
                return station.companyId.asc();
            }
            return station.companyId.desc();
        }

        return new OrderSpecifier(Order.ASC, NullExpression.DEFAULT, NullHandling.Default);
    }

    private Predicate toPredicate(SearchStationCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(companyId(condition.companyId()));
        builder.and(areaCode(condition.areaCode()));
        builder.and(areaDetailCode(condition.areaDetailCode()));
        builder.and(stationKindCode(condition.stationKindCode()));
        builder.and(stationKindDetailCode(condition.stationKindDetailCode()));
        builder.and(parkingFreeYn(condition.parkingFreeYn()));
        builder.and(useLimitYn(condition.useLimitYn()));
        builder.and(trafficYn(condition.trafficYn()));
        builder.and(deletedYn(condition.deletedYn()));

        return builder;
    }

    @Override
    public Slice<StationSummary> findAllStationSummary(Pageable pageable,
        SearchStationSummaryCondition condition) {

        var distanceFromCurrentLocation = distance(condition.currentLatitude(),
            condition.currentLongitude());

        List<StationSummary> contents = queryFactory
            .select(
                Projections.constructor(StationSummary.class,
                    Projections.constructor(StationSummary.Company.class,
                        company.id,
                        company.name),
                    station.id,
                    station.name,
                    station.address,
                    station.location,
                    station.latitude,
                    station.longitude,
                    distanceFromCurrentLocation.as("distance"),
                    station.useLimitYn,
                    station.parkingFreeYn,
                    station.stationKindCode,
                    stationSummary.chargerType))
            .from(station)
            .innerJoin(company).fetchJoin()
            .on(station.companyId.eq(company.id),
                company.deletedAt.isNull(),
                station.deletedAt.isNull())
            .innerJoin(stationSummary).fetchJoin()
            .on(station.id.eq(stationSummary.id))
            .where(toPredicate(condition))
            .orderBy(distanceFromCurrentLocation.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }

    @Override
    public Optional<StationDetail> findStationDetailById(String id) {
        StationDetail stationDetail = queryFactory.
            select(Projections.constructor(StationDetail.class,
                station.address,
                station.location,
                company.operator,
                company.tel,
                station.useTime,
                stationSummary.outputType,
                station.parkingFreeYn,
                station.useLimitYn
            ))
            .from(station)
            .innerJoin(company).fetchJoin()
            .on(station.companyId.eq(company.id),
                company.deletedAt.isNull(),
                station.deletedAt.isNull())
            .innerJoin(stationSummary).fetchJoin()
            .on(station.id.eq(stationSummary.id))
            .where(station.id.eq(id))
            .fetchOne();

        return Optional.ofNullable(stationDetail);
    }

    private Predicate toPredicate(SearchStationSummaryCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        var distanceFromBaseLocation = distance(condition.baseLatitude(),
            condition.baseLongitude());

        builder.and(distanceFromBaseLocation.loe(condition.radius()));
        builder.and(parkingFreeYn(condition.parkingFreeYn()));
        builder.and(useLimitYn(condition.useLimitYn()));
        builder.and(companyIdIn(condition.companyIds()));
        builder.and(chargerTypeBitAnd(condition.chargerTypes()));

        return builder;
    }
}