package com.zerobase.zbcharger.configuration.security;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class RequestMatchers {

    private static final String[] PERMIT_ALL_WHITELIST = {
        "/smartropay/**",
        "/members/register",
        "/member/email/verify",
        "/auth/token",
        "/error"
    };

    /**
     * JWT 인증(authentication) 확인에 사용되는 uri 패턴
     */
    public static final RequestMatcher JWT_FILTER_REQUEST_MATCHER = new AndRequestMatcher(
        Stream.concat(
            Stream.of(PERMIT_ALL_WHITELIST)
                .map(path -> new NegatedRequestMatcher(new AntPathRequestMatcher(path))),
            Stream.of(new AntPathRequestMatcher("/**"))
        ).collect(Collectors.toList()));

    /**
     * 권한(authorize) 확인이 필요 없는 uri 패턴
     */
    public static final RequestMatcher[] PERMIT_ALL_REQUEST_MATCHER =
        Stream.of(PERMIT_ALL_WHITELIST)
            .map(AntPathRequestMatcher::antMatcher)
            .toArray(AntPathRequestMatcher[]::new);
}
