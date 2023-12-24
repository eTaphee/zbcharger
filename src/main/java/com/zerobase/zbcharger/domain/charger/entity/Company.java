package com.zerobase.zbcharger.domain.charger.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

/**
 * 충전 회사
 */
@Entity
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Company extends AuditableEntity implements Persistable<String> {

    /**
     * 아이디
     */
    @Id
    private final String id;

    /**
     * 이름
     */
    private final String name;

    /**
     * 연락처
     */
    private final String tel;

    /**
     * 운영기관
     */
    private final String operator;

    @Transient
    @Setter
    private boolean isNew = true;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
