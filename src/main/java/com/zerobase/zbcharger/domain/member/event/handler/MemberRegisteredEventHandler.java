package com.zerobase.zbcharger.domain.member.event.handler;

import com.zerobase.zbcharger.domain.member.event.MemberRegisteredEvent;
import com.zerobase.zbcharger.domain.member.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 회원가입 이벤트 핸들러
 */
@Service
@RequiredArgsConstructor
public class MemberRegisteredEventHandler {

    private final EmailVerificationService emailVerificationService;

    @Async
    @TransactionalEventListener(classes = MemberRegisteredEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handle(MemberRegisteredEvent event) {
        emailVerificationService.sendVerificationEmail(event.getMemberId(), event.getEmail());
    }
}
