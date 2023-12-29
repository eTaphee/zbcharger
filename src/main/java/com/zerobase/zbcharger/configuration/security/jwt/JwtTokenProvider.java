package com.zerobase.zbcharger.configuration.security.jwt;

import com.zerobase.zbcharger.domain.auth.dto.AuthenticationDto;
import com.zerobase.zbcharger.domain.auth.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    /**
     * 액세스 토큰 유효시간 (1 시간 = 3600000 밀리초)
     */
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private static final String KEY_ROLES = "roles";
    /**
     * JWT 암호화 키
     */
    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    private SecretKey internalSecretKey;

    @PostConstruct
    private void initSecretKey() {
        if (internalSecretKey == null) {
            internalSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        }
    }

    /**
     * 토큰 생성
     *
     * @param authentication 인증 정보
     * @return 토큰
     */
    public TokenDto generateToken(AuthenticationDto authentication) {
        Claims claims = Jwts.claims()
            .subject(authentication.username())
            .add(KEY_ROLES, Collections.singleton(authentication.role()))
            .build();

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return new TokenDto(Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(expiredDate)
            .signWith(internalSecretKey, SIG.HS512)
            .compact());
    }

    /**
     * 액세스 토큰 클레임 파싱
     *
     * @param accessToken 액세스 토큰
     * @return 클레임
     */
    public Claims parseClaims(String accessToken) {
        return Jwts.parser().verifyWith(internalSecretKey).build()
            .parseSignedClaims(accessToken).getPayload();
    }
}
