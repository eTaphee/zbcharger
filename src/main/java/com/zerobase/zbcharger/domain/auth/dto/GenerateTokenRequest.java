package com.zerobase.zbcharger.domain.auth.dto;

import com.zerobase.zbcharger.validator.annotation.Email;
import com.zerobase.zbcharger.validator.annotation.Password;

/**
 * 토큰 생성 요청
 *
 * @param email    이메일
 * @param password 패스워드
 */
public record GenerateTokenRequest(
    @Email String email,
    @Password String password
) {

}
