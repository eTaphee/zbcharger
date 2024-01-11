package com.zerobase.zbcharger.domain.charger.dao.custom.impl;

import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.chargerTypeBitAnd;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.companyId;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.companyIdIn;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.distance;
import static com.zerobase.zbcharger.domain.charger.dao.expression.StationExpression.parkingFreeYn;
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
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.zbcharger.domain.charger.dao.custom.CustomStationRepository;
import com.zerobase.zbcharger.domain.charger.dto.admin.SearchStationRequest;
import com.zerobase.zbcharger.domain.charger.dto.client.SearchStationSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.client.StationSummary;
import com.zerobase.zbcharger.domain.charger.entity.Station;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import java.util.List;
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
    public Page<Station> findAll(Pageable pageable, SearchStationRequest request) {
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

    private Predicate toPredicate(SearchStationRequest request) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(companyId(request.companyId()));
        builder.and(areaCode(request.areaCode()));
        builder.and(areaDetailCode(request.areaDetailCode()));
//        builder.and(stationKindCode(request.stationKindCode()));
//        builder.and(stationKindDetailCode(request.stationKindDetailCode()));
//        builder.and(parkingFreeYn(request.parkingFreeYn()));
//        builder.and(useLimitYn(request.useLimitYn()));
//        builder.and(trafficYn(request.trafficYn()));
//        builder.and(deletedYn(request.deletedYn()));

        return builder;
    }


    private static BooleanExpression areaCode(String areaCode) {
        if (areaCode == null) {
            return null;
        }

        return null;
//        return station.areaCode.eq(areaCode);
    }

    private static BooleanExpression areaDetailCode(String areaDetailCode) {
        if (areaDetailCode == null) {
            return null;
        }

        return null;
//        return station.areaDetailCode.eq(areaDetailCode);
    }

    private static BooleanExpression stationKindCode(StationKindCode stationKind) {
        if (stationKind == null) {
            return null;
        }

        return station.stationKindCode.eq(stationKind);
    }

    private static BooleanExpression stationKindDetailCode(
        StationKindDetailCode stationKindDetailCode) {
        if (stationKindDetailCode == null) {
            return null;
        }

        return station.stationKindDetailCode.eq(stationKindDetailCode);
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