package com.zerobase.zbcharger.domain.member.controller;

import com.zerobase.zbcharger.domain.member.dto.RegisterMemberRequest;
import com.zerobase.zbcharger.domain.member.service.EmailVerificationService;
import com.zerobase.zbcharger.domain.member.service.RegisterMemberService;
import com.zerobase.zbcharger.validator.annotation.Email;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final RegisterMemberService registerMemberService;
    private final EmailVerificationService emailVerificationService;

    /**
     * 회원 가입
     */
    @PostMapping("/members/register")
    public void registerMember(@Valid @RequestBody RegisterMemberRequest request) {
        registerMemberService.registerMember(request);
    }

    /**
     * 이메일 인증
     *
     * @param token 인증 아이디
     * @param email 이메일
     */
    @GetMapping("/member/email/verify")
    public void verifyEmail(UUID token, @Email String email) {
        emailVerificationService.verifyEmail(token, email);
    }
}
