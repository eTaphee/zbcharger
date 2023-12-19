package com.zerobase.zbcharger.domain.member.event;

import com.zerobase.zbcharger.event.Event;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 인증 메일 전송 이벤트
 */
@RequiredArgsConstructor
@Getter
@ToString
public class EmailVerificationSendEvent extends Event {

    /**
     * 인증 아이디
     */
    private final UUID id;

    /**
     * 이메일
     */
    private final String email;
}
