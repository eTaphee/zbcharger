package com.zerobase.zbcharger.domain.member.dto;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.validator.annotation.Email;
import com.zerobase.zbcharger.validator.annotation.MemberName;
import com.zerobase.zbcharger.validator.annotation.Password;
import com.zerobase.zbcharger.validator.annotation.Phone;

/**
 * 회원 가입 요청
 *
 * @param email    이메일
 * @param password 패스워드
 * @param name     이름
 * @param phone    연락처
 */
public record RegisterMemberRequest
    (@Email String email,
     @Password String password,
     @MemberName String name,
     @Phone String phone) {

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .password(password)
            .name(name)
            .phone(phone)
            .build();
    }
}
