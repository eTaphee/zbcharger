package com.zerobase.zbcharger.exception.handler;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.ACCESS_DENIED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.ARGUMENT_NOT_VALID;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.UNHANDLED_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.zerobase.zbcharger.exception.CustomException;
import com.zerobase.zbcharger.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<ErrorResponse> handleBusinessException(
        HttpServletRequest request,
        CustomException e) {
        log.error("{} is occurred.", e.getErrorCode(), e);
        return new ResponseEntity<>(
            ErrorResponse.of(request.getRequestURI(), e.getErrorCode()),
            e.getErrorCode().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        HttpServletRequest request,
        MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occurred.", e);
        return new ResponseEntity<>(
            ErrorResponse.of(
                request.getRequestURI(),
                ARGUMENT_NOT_VALID,
                e.getFieldErrors()),
            BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    private ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(
        HttpServletRequest request,
        HandlerMethodValidationException e) {
        log.error("HandlerMethodValidationException is occurred.", e);
        return new ResponseEntity<>(
            ErrorResponse.of(
                request.getRequestURI(),
                ARGUMENT_NOT_VALID,
                null),
            BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ErrorResponse> handleHandlerAccessDeniedException(
        HttpServletRequest request,
        AccessDeniedException e) {
        log.error("AccessDeniedException is occurred.", e);
        return new ResponseEntity<>(
            ErrorResponse.of(
                request.getRequestURI(),
                ACCESS_DENIED,
                null),
            FORBIDDEN);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorResponse> handleRuntimeException(
        HttpServletRequest request,
        RuntimeException e) {
        log.error("RuntimeException is occurred.", e);
        return new ResponseEntity<>(
            ErrorResponse.of(request.getRequestURI(), UNHANDLED_ERROR),
            INTERNAL_SERVER_ERROR);
    }
}
