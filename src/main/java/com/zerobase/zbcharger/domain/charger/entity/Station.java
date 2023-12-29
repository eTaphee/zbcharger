package com.zerobase.zbcharger.domain.charger.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

/**
 * 충전소
 */
@Entity
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Station extends AuditableEntity implements Persistable<String> {

    /**
     * 아이디
     */
    @Id
    private final String id;

    /**
     * 회사 아이디
     */
    private final String companyId;

    /**
     * 충전소 이름
     */
    private final String name;

    /**
     * 주소
     */
    private final String address;

    /**
     * 이용 가능시간
     */
    private final String useTime;

    /**
     * 지역 코드
     */
    private final String areaCode;

    /**
     * 지역구분 상세 코드
     */
    private final String areaDetailCode;

    /**
     * 충전소 구분 코드
     */
    private final String stationKindCode;

    /**
     * 충전소 구분 상세 코드
     */
    private final String stationKindDetailCode;

    /**
     * 주차료 무료
     */
    private final boolean parkingFreeYn;

    /**
     * 안내
     */
    private final String note;

    /**
     * 이용자 제한
     */
    private final boolean useLimitYn;

    /**
     * 이용제한 사유
     */
    private final boolean useLimitDetail;

    /**
     * 편의제공 여부
     */
    private final boolean trafficYn;

    /**
     * 위도
     */
    @Column(name = "lat")
    private final Double latitude;

    /**
     * 경도
     */
    @Column(name = "lng")
    private final Double longitude;

    @Transient
    private boolean isNew = true;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setIsNewForPersistable(boolean isNew) {
        this.isNew = isNew;
    }
}
