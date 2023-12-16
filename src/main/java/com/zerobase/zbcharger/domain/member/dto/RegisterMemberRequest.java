package com.zerobase.zbcharger.domain.member.dto;

import com.zerobase.zbcharger.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 가입 요청
 *
 * @param email    이메일
 * @param password 패스워드
 * @param name     이름
 * @param phone    연락처
 */
public record RegisterMemberRequest
    (String email,
     String password,
     String name,
     String phone) {

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .password(password)
            .name(name)
            .phone(phone)
            .build();
    }
}
