package com.zerobase.zbcharger.domain.auth.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.AUTHENTICATION_INVALID;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.EMAIL_VERIFICATION_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.USER_NOT_VERIFIED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.zerobase.zbcharger.domain.auth.dto.AuthenticationDto;
import com.zerobase.zbcharger.domain.auth.dto.GenerateTokenRequest;
import com.zerobase.zbcharger.domain.member.dao.EmailVerificationRepository;
import com.zerobase.zbcharger.domain.member.dao.MemberRepository;
import com.zerobase.zbcharger.domain.member.entity.EmailVerification;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.member.entity.Role;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private AuthService authService;

    private final static Member MOCK_MEMBER = Member.builder()
        .email("email")
        .password("password")
        .role(Role.USER)
        .build();

    private final static EmailVerification MOCK_EMAIL_VERIFICATION = mock(EmailVerification.class);

    private final static GenerateTokenRequest MOCK_REQUEST = new GenerateTokenRequest("email",
        "password");

    @Test
    @DisplayName("인증 성공")
    void successAuthenticate() {
        // given
        given(memberRepository.findByEmail(any()))
            .willReturn(Optional.of(MOCK_MEMBER));

        given(passwordEncoder.matches(any(), any()))
            .willReturn(true);

        given(emailVerificationRepository.findByMemberEmail(any()))
            .willReturn(Optional.of(MOCK_EMAIL_VERIFICATION));

        given(MOCK_EMAIL_VERIFICATION.isVerifiedYn()).willReturn(true);

        // when
        AuthenticationDto authenticate = authService.authenticate(MOCK_REQUEST);

        // then
        assertEquals(MOCK_REQUEST.email(), authenticate.username());
        assertEquals(Role.USER, authenticate.role());
    }

    @Test
    @DisplayName("인증 실패 - 회원 정보 없음")
    void failAuthenticate_user_not_found() {
        // given
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(MOCK_MEMBER));

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> authService.authenticate(MOCK_REQUEST));

        // then
        assertEquals(AUTHENTICATION_INVALID, exception.getErrorCode());
    }

    @Test
    @DisplayName("인증 실패 - 비밀번호 불일치")
    void failAuthenticate_password_un_matched() {
        // given
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(MOCK_MEMBER));

        given(passwordEncoder.matches(any(), any()))
            .willReturn(false);

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> authService.authenticate(MOCK_REQUEST));

        // then
        assertEquals(AUTHENTICATION_INVALID, exception.getErrorCode());
    }

    @Test
    @DisplayName("인증 실패 - 인증 정보 없음")
    void failAuthenticate_email_verification_not_found() {
        // given
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(MOCK_MEMBER));

        given(passwordEncoder.matches(any(), any()))
            .willReturn(true);

        given(emailVerificationRepository.findByMemberEmail(any()))
            .willReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> authService.authenticate(MOCK_REQUEST));

        // then
        assertEquals(EMAIL_VERIFICATION_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("인증 실패 - 인증 안됨")
    void failAuthenticate_email_verification_not_verified() {
        // given
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(MOCK_MEMBER));

        given(passwordEncoder.matches(any(), any()))
            .willReturn(true);

        given(emailVerificationRepository.findByMemberEmail(any()))
            .willReturn(Optional.of(MOCK_EMAIL_VERIFICATION));

        given(MOCK_EMAIL_VERIFICATION.isVerifiedYn())
            .willReturn(false);

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> authService.authenticate(MOCK_REQUEST));

        // then
        assertEquals(USER_NOT_VERIFIED, exception.getErrorCode());
    }
}