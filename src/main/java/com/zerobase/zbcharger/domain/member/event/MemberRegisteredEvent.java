package com.zerobase.zbcharger.domain.member.event;

import com.zerobase.zbcharger.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원가입 이벤트
 */
@RequiredArgsConstructor
@Getter
public class MemberRegisteredEvent extends Event {

    /**
     * 회원 아이디
     */
    private final Long memberId;

    /**
     * 이메일
     */
    private final String email;
}
