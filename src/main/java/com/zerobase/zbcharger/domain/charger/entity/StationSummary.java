package com.zerobase.zbcharger.domain.charger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;


/**
 * 충전기 데이터 기반 충전소 요약 정보
 */
@Entity
@Subselect("""
        select
        station_id,
        bit_or(charger_type) as charger_type,
        bit_or(case when output < 50 then 1 else 2 end) as output_type,
        count(case when output < 50 then 1 end) as 'slow_count',
        count(case when output < 50 and stat = 2 then 1 end) as 'available_slow_count',
        count(case when output >= 50 then 1 end) as 'fast_count',
        count(case when output >= 50 and stat = 2 then 1 end) as 'available_fast_count'
        from charger
        where deleted_yn = 0
        group by station_id
    """)
@Immutable
@Synchronize("charger")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class StationSummary {

    /**
     * 충전소 아이디
     */
    @Id
    @Column(name = "station_id")
    private final String id;

    /**
     * 충전기 타입(queyrdsl 에서 사용하기 위해 EnumSet 대신 int 로 사용) 1: 완속, 2: 급속
     */
    private final int outputType;

    /**
     * 지원 충전기 타입(queyrdsl 에서 사용하기 위해 EnumSet 대신 int 로 사용)
     * <p>
     * '@Convert(converter = ChargerTypeConverter.class) 사용 불가능
     */
    private final int chargerType;

    /**
     * 완속 충전기 수
     */
    private final int slowCount;

    /**
     * 사용가능 완속 충전기 수
     */
    private final int availableSlowCount;

    /**
     * 급속 충전기 수
     */
    private final int fastCount;

    /**
     * 사용가능 급속 충전기 수
     */
    private final int availableFastCount;
}
