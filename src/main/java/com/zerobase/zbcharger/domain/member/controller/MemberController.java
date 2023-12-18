package com.zerobase.zbcharger.domain.member.controller;

import com.zerobase.zbcharger.domain.member.dto.RegisterMemberRequest;
import com.zerobase.zbcharger.domain.member.service.RegisterMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final RegisterMemberService registerMemberService;

    /**
     * 회원 가입
     */
    @PostMapping("register")
    public void registerMember(@Valid @RequestBody RegisterMemberRequest request) {
        registerMemberService.registerMember(request);
    }
}
