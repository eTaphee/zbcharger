package com.zerobase.zbcharger.domain.auth.dto;

/**
 * 토큰 생성 응답
 *
 * @param accessToken 액세스 토큰
 */
public record GenerateTokenResponse(
    String accessToken
) {

    public static GenerateTokenResponse from(TokenDto tokenDto) {
        return new GenerateTokenResponse(tokenDto.accessToken());
    }
}
