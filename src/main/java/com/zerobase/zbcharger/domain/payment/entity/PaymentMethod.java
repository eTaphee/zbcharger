package com.zerobase.zbcharger.domain.payment.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 수단
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "method")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PaymentMethod extends AuditableEntity {

    /**
     * pk
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 회원 pk
     */
    private final Long memberId;

    /**
     * 기본 결제수단 여부
     */
    private boolean isPrimary;

    /**
     * 결제수단
     */
    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private final PayMethod method;

    public PaymentMethod(Long memberId, PayMethod method) {
        this.memberId = memberId;
        this.method = method;
    }

    /**
     * 기본 결제수단 여부 설정
     *
     * @param isPrimary
     */
    public void setPrimary() {
        this.isPrimary = true;
    }

    /**
     * 기본 결제수단 여부 해제
     */
    public void unsetPrimary() {
        this.isPrimary = false;
    }
}
