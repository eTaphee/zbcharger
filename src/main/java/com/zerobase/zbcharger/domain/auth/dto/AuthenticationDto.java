package com.zerobase.zbcharger.domain.auth.dto;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.member.entity.Role;

/**
 * 인증 정보
 *
 * @param username 아이디
 * @param role     역할
 */
public record AuthenticationDto(
    String username,
    Role role
) {

    public static AuthenticationDto fromEntity(Member member) {
        return new AuthenticationDto(member.getEmail(), member.getRole());
    }
}
