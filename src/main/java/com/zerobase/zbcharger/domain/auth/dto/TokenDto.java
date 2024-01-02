package com.zerobase.zbcharger.domain.auth.dto;

import com.zerobase.zbcharger.domain.auth.entity.Token;

/**
 * 토큰
 *
 * @param accessToken  액세스 토큰
 * @param refreshToken 리프레시 토큰
 */
public record TokenDto(
    String accessToken,
    String refreshToken) {

    public static TokenDto fromEntity(Token token) {
        return new TokenDto(token.getAccessToken(), token.getRefreshToken());
    }
}
