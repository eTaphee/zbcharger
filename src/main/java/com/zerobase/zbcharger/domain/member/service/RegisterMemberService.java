package com.zerobase.zbcharger.domain.member.service;

import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.member.dto.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 가입 서비스
 */
@Service
@RequiredArgsConstructor
public class RegisterMemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public void registerMember(RegisterMemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new RuntimeException("중복 이메일 존재");
        }

        memberRepository.save(request.toEntity());

        // TODO: 인증 메일 전송
    }
}
