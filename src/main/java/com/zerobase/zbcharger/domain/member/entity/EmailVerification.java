package com.zerobase.zbcharger.domain.member.entity;

import com.zerobase.zbcharger.domain.common.entity.AuditableEntity;
import com.zerobase.zbcharger.domain.member.event.EmailVerificationSendEvent;
import com.zerobase.zbcharger.event.Events;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    private static final long RETRY_MIN = 5L;

    /**
     * 토큰
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 회원
     */
    @OneToOne(fetch = FetchType.LAZY)
    private final Member member;

    /**
     * 인증 만료 일시
     */
    private LocalDateTime expiredAt;

    /**
     * 인증 여부
     */
    private boolean verifiedYn;

    /**
     * 인증 일시
     */
    private LocalDateTime verifiedAt;

    /**
     * 메일 발송 횟수
     */
    private int sendCount;

    /**
     * 마지막 메일 발송 일시
     */
    private LocalDateTime lastSentAt;

    public EmailVerification(Member member) {
        this.member = member;
    }

    /**
     * 인증 메일 전송
     */
    public void sendMail() {
        alreadyVerified();
        checkRetryTime();

        sendCount++;
        lastSentAt = LocalDateTime.now();
        expiredAt = lastSentAt.plusHours(MAX_EXPIRE_HOUR);

        Events.raise(new EmailVerificationSendEvent(id, member.getEmail()));
    }

    /**
     * 인증 여부 확인
     */
    private void alreadyVerified() {
        if (isVerifiedYn()) {
            // TODO: 커스텀 예외
            throw new RuntimeException("already verified");
        }
    }

    /**
     * 재전송 가능 시간 확인
     */
    private void checkRetryTime() {
        if (lastSentAt != null && LocalDateTime.now().isAfter(lastSentAt.plusMinutes(RETRY_MIN))) {
            // TODO: 커스텀 예외
            throw new RuntimeException("retry disable");
        }
    }

    /**
     * 인증 시간 초과 확인
     */
    private void checkExpiredAt() {
        if (expiredAt != null && LocalDateTime.now().isAfter(expiredAt)) {
            // TODO: 커스텀 예외
            throw new RuntimeException("expired");
        }
    }

    /**
     * 인증
     */
    public void verify() {
        alreadyVerified();
        checkExpiredAt();

        verifiedYn = true;
        verifiedAt = LocalDateTime.now();
    }
}
