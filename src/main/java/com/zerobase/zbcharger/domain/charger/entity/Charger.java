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
 * 충전기
 */
@Getter
@Entity
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Charger extends AuditableEntity implements Persistable<String> {

    /**
     * 아이디
     */
    @Id
    private final String id;

    /**
     * 충전소 아이디
     */
    private final String stationId;

    /**
     * 충전기 타입
     */
    @Column(columnDefinition = "char(2)")
    private String chargerType;

    /**
     * 위치
     */
    private String location;

    /**
     * 상태
     */
    private int stat;

    /**
     * 충전 용량
     */
    private String output;

    /**
     * 충전 방식
     */
    private String method;

    /**
     * 상태 갱신 일시
     */
    private LocalDateTime statUpdatedAt;

    /**
     * 마지막 충전시작 일시
     */
    private LocalDateTime lastChargeStartedAt;

    /**
     * 마지막 충전종료 일시
     */
    private LocalDateTime lastChargeFinishedAt;

    /**
     * 현재 충전시작 일시
     */
    private LocalDateTime nowChargeStartedAt;

    /**
     * 삭제 여부
     */
    private boolean deletedYn;

    /**
     * 삭제 사유
     */
    private String deleteDetail;

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

    public void delete() {
        this.deletedYn = true;
    }

    public void restore() {
        this.deletedYn = false;
    }

    public void update(String chargerType, String location, String output, String method) {
        this.chargerType = chargerType;
        this.location = location;
        this.output = output;
        this.method = method;
    }
}
