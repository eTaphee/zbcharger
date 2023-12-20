package com.zerobase.zbcharger.domain.member.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.EMAIL_VERIFICATION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.zerobase.zbcharger.domain.member.dao.EmailVerificationRepository;
import com.zerobase.zbcharger.domain.member.entity.EmailVerification;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Test
    @DisplayName("이메일 인증 성공")
    void successVerifyEmail() {
        // given
        EmailVerification emailVerification = new EmailVerification(Member.builder().build());

        given(emailVerificationRepository.findByIdAndMemberEmail(any(), any()))
            .willReturn(Optional.of(emailVerification));

        // when
        // then
        emailVerificationService.verifyEmail(any(), any());
    }

    @Test
    @DisplayName("이메일 인증 실패 - 인증 정보 없음")
    void failVerifyEmail_emailVerification_not_found() {
        // given
        given(emailVerificationRepository.findByIdAndMemberEmail(any(), any()))
            .willReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> emailVerificationService.verifyEmail(any(), any()));

        // then
        assertEquals(EMAIL_VERIFICATION_NOT_FOUND, exception.getErrorCode());
    }
}