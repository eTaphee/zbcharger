package com.zerobase.zbcharger.configuration.security.filter;

import static com.zerobase.zbcharger.configuration.security.RequestMatchers.JWT_FILTER_REQUEST_MATCHER;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.TOKEN_EXPIRED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.TOKEN_INVALID;

import com.zerobase.zbcharger.configuration.security.jwt.JwtAuthenticationException;
import com.zerobase.zbcharger.configuration.security.jwt.JwtAuthenticationToken;
import com.zerobase.zbcharger.configuration.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.ObjectUtils;

/**
 * JWT 인증 필터
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String TOKEN_HEADER = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        super(JWT_FILTER_REQUEST_MATCHER);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException {

        String accessToken = obtainAccessToken(request);
        if (ObjectUtils.isEmpty(accessToken)) {
            return null;
        }
        Claims claims = obtainClaims(accessToken);

        JwtAuthenticationToken authRequest = JwtAuthenticationToken.unauthenticated(
            claims.getSubject(),
            accessToken);

        return this.getAuthenticationManager().authenticate(authRequest);

    }

    @Nullable
    private String obtainAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(authorization) && authorization.startsWith(TOKEN_PREFIX)) {
            return authorization.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    private Claims obtainClaims(String accessToken) {
        try {
            return tokenProvider.parseClaims(accessToken);
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new JwtAuthenticationException(TOKEN_INVALID);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
