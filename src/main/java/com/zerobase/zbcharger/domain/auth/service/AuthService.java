package com.zerobase.zbcharger.domain.auth.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.AUTHENTICATION_INVALID;

import com.zerobase.zbcharger.domain.auth.dto.AuthenticationDto;
import com.zerobase.zbcharger.domain.auth.dto.GenerateTokenRequest;
import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
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

    /**
     * 사용자 인증
     *
     * @param request 로그인 정보
     * @return 인증 정보
     */
    public AuthenticationDto authenticate(GenerateTokenRequest request) {
        Member member = memberRepository.findByEmail(request.email())
            .orElseThrow(() -> new CustomException(AUTHENTICATION_INVALID));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new CustomException(AUTHENTICATION_INVALID);
        }

        return AuthenticationDto.fromEntity(member);
    }
}
