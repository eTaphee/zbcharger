package com.zerobase.zbcharger.domain.charger.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

/**
 * 충전소
 */
@Getter
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
    @Column(columnDefinition = "char(2)")
    private final String companyId;

    /**
     * 충전소 이름
     */
    private String name;

    /**
     * 주소
     */
    private String address;

    /**
     * 이용 가능시간
     */
    private String useTime;

    /**
     * 지역 코드
     */
    @Column(columnDefinition = "char(2)")
    private String areaCode;

    /**
     * 지역구분 상세 코드
     */
    @Column(columnDefinition = "char(5)")
    private String areaDetailCode;

    /**
     * 충전소 구분 코드
     */
    @Column(columnDefinition = "char(2)")
    private String stationKindCode;

    /**
     * 충전소 구분 상세 코드
     */
    @Column(columnDefinition = "char(4)")
    private String stationKindDetailCode;

    /**
     * 주차료 무료
     */
    private boolean parkingFreeYn;

    /**
     * 안내
     */
    private String note;

    /**
     * 이용자 제한
     */
    private boolean useLimitYn;

    /**
     * 이용제한 사유
     */
    private String useLimitDetail;

    /**
     * 편의제공 여부
     */
    private boolean trafficYn;

    /**
     * 위도
     */
    @Column(name = "lat")
    private Double latitude;

    /**
     * 경도
     */
    @Column(name = "lng")
    private Double longitude;

    /**
     * 삭제일시
     */
    private LocalDateTime deletedAt;

    @Transient
    @Builder.Default
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

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deletedAt = null;
    }


    public void update(String name, String address, String useTime, String areaCode,
        String areaDetailCode, String stationKindCode, String stationKindDetailCode,
        boolean parkingFreeYn, String note, boolean useLimitYn, String useLimitDetail,
        boolean trafficYn, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.useTime = useTime;
        this.areaCode = areaCode;
        this.areaDetailCode = areaDetailCode;
        this.stationKindCode = stationKindCode;
        this.stationKindDetailCode = stationKindDetailCode;
        this.parkingFreeYn = parkingFreeYn;
        this.note = note;
        this.useLimitYn = useLimitYn;
        this.useLimitDetail = useLimitDetail;
        this.trafficYn = trafficYn;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
