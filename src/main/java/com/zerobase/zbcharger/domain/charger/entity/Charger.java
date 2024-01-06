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
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

/**
 * 충전기
 */
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
    private final String chargerType;

    /**
     * 위치
     */
    private final String location;

    /**
     * 상태
     */
    private final int stat;

    /**
     * 충전 용량
     */
    private final String output;

    /**
     * 충전 방식
     */
    private final String method;

    /**
     * 상태 갱신 일시
     */
    private final LocalDateTime statUpdatedAt;

    /**
     * 마지막 충전시작 일시
     */
    private final LocalDateTime lastChargeStartedAt;

    /**
     * 마지막 충전종료 일시
     */
    private final LocalDateTime lastChargeFinishedAt;

    /**
     * 현재 충전시작 일시
     */
    private final LocalDateTime nowChargeStartedAt;

    /**
     * 삭제 여부
     */
    private final boolean deletedYn;

    /**
     * 삭제 사유
     */
    private final String deleteDetail;

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
}
