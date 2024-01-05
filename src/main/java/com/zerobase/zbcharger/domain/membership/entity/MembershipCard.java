package com.zerobase.zbcharger.domain.membership.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import com.zerobase.zbcharger.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버십 카드
 */
@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class MembershipCard extends AuditableEntity {

    /**
     * 카드 번호
     */
    @Id
    @Column(columnDefinition = "char(19)")
    private final String cardNo;

    /**
     * 회원
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private final Member member;

    /**
     * 카드 종류
     */
    @Enumerated(EnumType.STRING)
    private final CardType cardType;

    /**
     * 카드 사용등록 일시
     */
    private LocalDateTime registeredAt;

    @Builder
    private MembershipCard(String cardNo, Member member, CardType cardType) {
        this.cardNo = cardNo;
        this.member = member;
        this.cardType = cardType;

        if (cardType == CardType.MOBILE) {
            this.registeredAt = LocalDateTime.now();
        }
    }

    /**
     * 카드 등록
     */
    public void register() {
        this.registeredAt = LocalDateTime.now();
    }
}
