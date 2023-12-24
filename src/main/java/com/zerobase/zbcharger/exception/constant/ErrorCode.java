package com.zerobase.zbcharger.exception.constant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(CONFLICT, "중복되는 메일이 존재합니다."),

    EMAIL_VERIFICATION_NOT_FOUND(NOT_FOUND, "이메일 인증 정보를 찾을 수 없습니다."),

    ALREADY_EMAIL_VERIFIED(BAD_REQUEST, "인증이 완료된 요청입니다."),
    EMAIL_VERIFICATION_EXPIRED(BAD_REQUEST, "인증 시간이 만료됐습니다. 인증 메일을 재발송하세요."),
    EMAIL_VERIFICATION_RESEND_TIME_EXCEED(BAD_REQUEST, "인증 메일 재전송 가능시간을 초과했습니다. 잠시후 다시 시도하세요."),
    ARGUMENT_NOT_VALID(BAD_REQUEST, "잘못된 입력입니다."),

    UNHANDLED_ERROR(INTERNAL_SERVER_ERROR, "정의되지 않은 에러 발생");

    /**
     * 응답 상태
     */
    private final HttpStatus status;

    /**
     * 메시지
     */
    private final String message;
}
