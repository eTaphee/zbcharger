package com.zerobase.zbcharger.configuration.security.jwt;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.EMAIL_VERIFICATION_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.USER_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.USER_NOT_VERIFIED;

import com.zerobase.zbcharger.domain.member.dao.EmailVerificationRepository;
import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.member.entity.EmailVerification;
import com.zerobase.zbcharger.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * JWT 인증 사용자 서비스
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
            .orElseThrow(() -> new JwtAuthenticationException(USER_NOT_FOUND));

        checkEmailVerified(member);

        return new JwtUserDetails(member);
    }

    private void checkEmailVerified(Member member) {
        EmailVerification emailVerification = emailVerificationRepository
            .findByMemberEmail(member.getEmail())
            .orElseThrow(() -> new JwtAuthenticationException(EMAIL_VERIFICATION_NOT_FOUND));

        if (!emailVerification.isVerifiedYn()) {
            throw new JwtAuthenticationException(USER_NOT_VERIFIED);
        }
    }
}
