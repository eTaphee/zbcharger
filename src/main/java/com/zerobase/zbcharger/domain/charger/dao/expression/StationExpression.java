package com.zerobase.zbcharger.domain.charger.dao.expression;

import static com.zerobase.zbcharger.domain.charger.entity.QCompany.company;
import static com.zerobase.zbcharger.domain.charger.entity.QStation.station;
import static com.zerobase.zbcharger.domain.charger.entity.QStationSummary.stationSummary;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import java.util.Set;

public final class StationExpression {

    public static BooleanExpression parkingFreeYn(Boolean parkingFreeYn) {
        return (parkingFreeYn != null) ? station.parkingFreeYn.eq(parkingFreeYn) : null;
    }

    public static BooleanExpression useLimitYn(Boolean useLimitYn) {
        return (useLimitYn != null) ? station.useLimitYn.eq(useLimitYn) : null;
    }

    public static BooleanExpression trafficYn(Boolean trafficYn) {
        return (trafficYn != null) ? station.useLimitYn.eq(trafficYn) : null;
    }

    public static BooleanExpression deletedYn(Boolean deletedYn) {
        if (deletedYn == null) {
            return null;
        }

        if (deletedYn) {
            return station.deletedAt.isNotNull();
        }

        return station.deletedAt.isNull();
    }

    public static BooleanExpression companyIdIn(Set<String> companyIds) {
        if (companyIds == null || companyIds.isEmpty()) {
            return null;
        }

        return company.id.in(companyIds);
    }

    public static BooleanExpression companyId(String companyId) {
        return (companyId != null) ? station.companyId.eq(companyId) : null;
    }

    public static BooleanBuilder chargerTypeBitAnd(Set<ChargerType> chargerTypes) {
        if (chargerTypes == null || chargerTypes.isEmpty()) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();

        chargerTypes
            .stream()
            .map(m -> Expressions.numberTemplate(Integer.class, "bitand({0}, {1})",
                    stationSummary.chargerType,
                    Expressions.asNumber(m.getValue()))
                .eq(m.getValue())
            ).forEach(builder::or);

        return builder;
    }

    public static BooleanExpression stationKindIn(Set<StationKindCode> stationKindCodes) {
        if (stationKindCodes == null || stationKindCodes.isEmpty()) {
            return null;
        }

        return station.stationKindCode.in(stationKindCodes);
    }

    public static NumberExpression<Double> distance(double latitude,
        double longitude) {
        return Expressions.numberTemplate(Double.class, "ST_Distance_Sphere({0}, {1})",
            Expressions.stringTemplate("POINT({0}, {1})", longitude, latitude),
            Expressions.stringTemplate("POINT({0}, {1})", station.longitude, station.latitude));
    }

    public static BooleanExpression areaCode(AreaCode areaCode) {
        return (areaCode != null) ? station.areaCode.eq(areaCode) : null;
    }

    public static BooleanExpression areaDetailCode(AreaDetailCode areaDetailCode) {
        return (areaDetailCode != null) ? station.areaDetailCode.eq(areaDetailCode) : null;
    }

    public static BooleanExpression stationKindCode(StationKindCode stationKind) {
        return (stationKind != null) ? station.stationKindCode.eq(stationKind) : null;
    }

    public static BooleanExpression stationKindDetailCode(
        StationKindDetailCode stationKindDetailCode) {
        return (stationKindDetailCode != null) ?
            station.stationKindDetailCode.eq(stationKindDetailCode) : null;
    }
}
