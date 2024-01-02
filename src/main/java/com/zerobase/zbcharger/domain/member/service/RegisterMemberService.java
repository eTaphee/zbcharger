package com.zerobase.zbcharger.domain.member.service;

import static com.zerobase.zbcharger.domain.member.entity.Role.USER;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.EMAIL_ALREADY_EXISTS;

import com.zerobase.zbcharger.domain.member.dao.EmailVerificationRepository;
import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.member.dto.RegisterMemberRequest;
import com.zerobase.zbcharger.domain.member.entity.EmailVerification;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.exception.CustomException;
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
    private final EmailVerificationRepository emailVerificationRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public void registerMember(RegisterMemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }

        Member member = memberRepository.save(createMember(request));

        EmailVerification emailVerification = emailVerificationRepository.save(
            new EmailVerification(member));

        emailVerification.sendMail();
    }

    private Member createMember(RegisterMemberRequest request) {
        return Member.builder()
            .email(request.email())
            .phone(request.phone())
            .name(request.name())
            .password(passwordEncoder.encode(request.password()))
            .role(USER)
            .build();
    }
}
