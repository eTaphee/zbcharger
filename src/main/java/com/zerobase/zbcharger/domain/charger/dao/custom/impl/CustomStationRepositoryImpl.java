package com.zerobase.zbcharger.domain.charger.dao.custom.impl;

import static com.zerobase.zbcharger.domain.charger.entity.QCompany.company;
import static com.zerobase.zbcharger.domain.charger.entity.QStation.station;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.zbcharger.domain.charger.dao.custom.CustomStationRepository;
import com.zerobase.zbcharger.domain.charger.dto.admin.SearchStationRequest;
import com.zerobase.zbcharger.domain.charger.entity.Station;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        builder.and(stationKindCode(request.stationKindCode()));
        builder.and(stationKindDetailCode(request.stationKindDetailCode()));
        builder.and(parkingFreeYn(request.parkingFreeYn()));
        builder.and(useLimitYn(request.useLimitYn()));
        builder.and(trafficYn(request.trafficYn()));
        builder.and(deletedYn(request.deletedYn()));

        return builder;
    }

    private static BooleanExpression companyId(String companyId) {
        if (companyId == null) {
            return null;
        }

        return station.companyId.eq(companyId);
    }

    private static BooleanExpression areaCode(String areaCode) {
        if (areaCode == null) {
            return null;
        }

        return station.areaCode.eq(areaCode);
    }

    private static BooleanExpression areaDetailCode(String areaDetailCode) {
        if (areaDetailCode == null) {
            return null;
        }

        return station.areaDetailCode.eq(areaDetailCode);
    }

    private static BooleanExpression stationKindCode(String stationKindCode) {
        if (stationKindCode == null) {
            return null;
        }

        return station.stationKindCode.eq(stationKindCode);
    }

    private static BooleanExpression stationKindDetailCode(String stationKindDetailCode) {
        if (stationKindDetailCode == null) {
            return null;
        }

        return station.stationKindDetailCode.eq(stationKindDetailCode);
    }

    private static BooleanExpression parkingFreeYn(Boolean parkingFreeYn) {
        if (parkingFreeYn == null) {
            return null;
        }

        if (parkingFreeYn) {
            return station.parkingFreeYn.isTrue();
        }

        return station.parkingFreeYn.isFalse();
    }

    private static BooleanExpression useLimitYn(Boolean useLimitYn) {
        if (useLimitYn == null) {
            return null;
        }

        if (useLimitYn) {
            return station.useLimitYn.isTrue();
        }

        return station.useLimitYn.isFalse();
    }

    private static BooleanExpression trafficYn(Boolean trafficYn) {
        if (trafficYn == null) {
            return null;
        }

        if (trafficYn) {
            return station.trafficYn.isTrue();
        }

        return station.trafficYn.isFalse();
    }

    private static BooleanExpression deletedYn(Boolean deletedYn) {
        if (deletedYn == null) {
            return null;
        }

        if (deletedYn) {
            return station.deletedAt.isNotNull();
        }

        return station.deletedAt.isNull();
    }
}
