package com.zerobase.zbcharger.configuration.security.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * JWT 인증용 토큰
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 사용자 정보
     */
    private final Object principal;

    /**
     * JWT
     */
    private final Object credentials;


    /**
     * JWT 인증 전 객체 생성자
     *
     * @param principal   사용자 정보
     * @param credentials JWT
     */
    private JwtAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * JWT 인증 완료 객체 생성자
     *
     * @param principal   사용자 정보
     * @param credentials JWT
     * @param authorities 권한 목록
     */
    private JwtAuthenticationToken(Object principal, Object credentials,
        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    /**
     * 인증 전 토큰 생성 팩토리 메서드
     *
     * @param principal   사용자 정보
     * @param credentials JWT
     * @return 인증 전 토크
     */
    public static JwtAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new JwtAuthenticationToken(principal, credentials);
    }

    /**
     * 인증 후 토큰 생성 팩토리 메섣,
     *
     * @param principal   사용자 정보
     * @param credentials JWT
     * @param authorities 권한 목록
     * @return 인증 후 토큰
     */
    public static JwtAuthenticationToken authenticated(Object principal, Object credentials,
        Collection<? extends GrantedAuthority> authorities) {
        return new JwtAuthenticationToken(principal, credentials, authorities);
    }
}
