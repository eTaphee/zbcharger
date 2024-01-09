package com.zerobase.zbcharger.configuration.security;

import static com.zerobase.zbcharger.configuration.security.RequestMatchers.PERMIT_ALL_REQUEST_MATCHER;

import com.zerobase.zbcharger.configuration.security.filter.JwtAuthenticationFilter;
import com.zerobase.zbcharger.configuration.security.handler.JwtAuthenticationFailureHandler;
import com.zerobase.zbcharger.configuration.security.handler.JwtAuthenticationSuccessHandler;
import com.zerobase.zbcharger.configuration.security.jwt.JwtAuthenticationProvider;
import com.zerobase.zbcharger.configuration.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final AuthenticationConfiguration configuration;

    private final JwtTokenProvider tokenProvider;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Custom Authentication Provider 등록
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        ProviderManager manager = (ProviderManager) configuration.getAuthenticationManager();
        manager.getProviders().add(jwtAuthenticationProvider);
        return configuration.getAuthenticationManager();
    }

    /**
     * JWT 인증 필터 빈 등록
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(tokenProvider);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .frameOptions(FrameOptionsConfig::disable))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PERMIT_ALL_REQUEST_MATCHER).permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
