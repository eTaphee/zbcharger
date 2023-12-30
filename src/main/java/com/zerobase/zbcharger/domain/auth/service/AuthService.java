package com.zerobase.zbcharger.domain.auth.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.AUTHENTICATION_INVALID;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.EMAIL_VERIFICATION_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.USER_NOT_VERIFIED;

import com.zerobase.zbcharger.domain.auth.dto.AuthenticationDto;
import com.zerobase.zbcharger.domain.auth.dto.GenerateTokenRequest;
import com.zerobase.zbcharger.domain.member.dao.EmailVerificationRepository;
import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.member.entity.EmailVerification;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 인증 서비스
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final EmailVerificationRepository emailVerificationRepository;

    /**
     * 사용자 인증
     *
     * @param request 로그인 정보
     * @return 인증 정보
     */
    public AuthenticationDto authenticate(GenerateTokenRequest request) {
        Member member = memberRepository.findByEmail(request.email())
            .orElseThrow(() -> new CustomException(AUTHENTICATION_INVALID));

        checkPasswordMatched(request.password(), member.getPassword());
        checkEmailVerified(member);

        return AuthenticationDto.fromEntity(member);
    }

    private void checkPasswordMatched(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(AUTHENTICATION_INVALID);
        }
    }

    private void checkEmailVerified(Member member) {
        EmailVerification emailVerification = emailVerificationRepository
            .findByMemberEmail(member.getEmail())
            .orElseThrow(() -> new CustomException(EMAIL_VERIFICATION_NOT_FOUND));

        if (!emailVerification.isVerifiedYn()) {
            throw new CustomException(USER_NOT_VERIFIED);
        }
    }
}
