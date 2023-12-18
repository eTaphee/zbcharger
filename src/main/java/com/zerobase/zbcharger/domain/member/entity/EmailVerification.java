package com.zerobase.zbcharger.domain.member.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 인증
 */
@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class EmailVerification extends AuditableEntity {

    private static final long MAX_EXPIRE_HOUR = 3L;

    /**
     * 토큰
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 회원 아이디
     */
    private final long memberId;

    /**
     * 인증 만료 일시
     */
    private final LocalDateTime expiredAt;

    /**
     * 인증 여부
     */
    private boolean verifiedYn;

    public EmailVerification(long memberId) {
        this.memberId = memberId;
        this.expiredAt = LocalDateTime.now().plusHours(MAX_EXPIRE_HOUR);
    }
}
