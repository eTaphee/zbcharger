package com.zerobase.zbcharger.domain.auth.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 토큰
 */
@Getter
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 7)
public class Token {

    /**
     * 사용자 아이디
     */
    @Id
    private final String id;

    /**
     * 액세스 토큰
     */
    private final String accessToken;

    /**
     * 리프레시 토큰
     */
    @Indexed
    private final String refreshToken;

    public Token(String id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
