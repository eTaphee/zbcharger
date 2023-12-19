package com.zerobase.zbcharger.domain.member.event.handler;

import com.zerobase.zbcharger.domain.member.event.EmailVerificationSendEvent;
import com.zerobase.zbcharger.domain.member.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 이메일 전송 이벤트 핸들러
 */
@Service
@RequiredArgsConstructor
public class EmailVerificationSendEventHandler {
    private final EmailVerificationService emailVerificationService;

    @Async
    @TransactionalEventListener(classes = EmailVerificationSendEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handle(EmailVerificationSendEvent event) {
        emailVerificationService.sendVerificationEmail(event.getId(), event.getEmail());
    }
}
