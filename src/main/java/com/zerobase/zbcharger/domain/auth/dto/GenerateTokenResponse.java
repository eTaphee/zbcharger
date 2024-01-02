package com.zerobase.zbcharger.domain.auth.dto;

/**
 * 토큰 생성 응답
 *
 * @param accessToken  액세스 토큰
 * @param refreshToken 리프레시 토큰
 */
public record GenerateTokenResponse(
    String accessToken,
    String refreshToken
) {

    public static GenerateTokenResponse from(TokenDto token) {
        return new GenerateTokenResponse(token.accessToken(), token.refreshToken());
    }
}
