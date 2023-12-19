package com.zerobase.zbcharger.domain.member.service;

import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.member.dto.RegisterMemberRequest;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.member.event.MemberRegisteredEvent;
import com.zerobase.zbcharger.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 가입 서비스
 */
@Service
@RequiredArgsConstructor
public class RegisterMemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public void registerMember(RegisterMemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new RuntimeException("중복 이메일 존재");
        }

        Member member = memberRepository.save(createMember(request));

        Events.raise(new MemberRegisteredEvent(member.getId(), member.getEmail()));
    }

    private Member createMember(RegisterMemberRequest request) {
        return Member.builder()
            .email(request.email())
            .phone(request.phone())
            .name(request.name())
            .password(passwordEncoder.encode(request.password()))
            .build();
    }
}
