package com.zerobase.zbcharger.configuration.security.handler;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.UNHANDLED_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.zbcharger.configuration.security.jwt.JwtAuthenticationException;
import com.zerobase.zbcharger.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * JWT 인증 실패 핸들러
 */
@Slf4j
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) {
        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse;

        if (exception instanceof JwtAuthenticationException) {
            errorResponse = ErrorResponse.of(request.getRequestURI(),
                ((JwtAuthenticationException) exception).getErrorCode());
        } else {
            errorResponse = ErrorResponse.of(request.getRequestURI(), UNHANDLED_ERROR);
        }

        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException ex) {
            log.error("IOException is occurred.", ex);
        }
    }
}
