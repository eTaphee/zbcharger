package com.zerobase.zbcharger.configuration.security.jwt;

import com.zerobase.zbcharger.exception.constant.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * JWT 인증 예외
 */
@Getter
public class JwtAuthenticationException extends AuthenticationException {

    private final ErrorCode errorCode;

    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
