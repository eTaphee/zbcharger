package com.zerobase.zbcharger.exception.constant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_VERIFIED(UNAUTHORIZED, "인증된 사용자가 아닙니다."),
    USER_NOT_FOUND(UNAUTHORIZED, "사용자를 찾을 수 없습니다."),
    AUTHENTICATION_INVALID(UNAUTHORIZED, "아이디를 찾을 수 없거나, 비밀번호가 일치하지 않습니다."),
    TOKEN_INVALID(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(UNAUTHORIZED, "토큰이 만료됐습니다."),
    TOKEN_REQUIRED(UNAUTHORIZED, "토큰이 필요합니다."),

    MEMBERSHIP_CARD_ALREADY_ISSUED(BAD_REQUEST, "멤버십 카드를 이미 발급받았습니다."),
    MEMBERSHIP_CARD_ALREADY_REGISTERED(BAD_REQUEST, "이미 등록된 멤버십 카드입니다."),
    MEMBERSHIP_CARD_NOT_FOUND(NOT_FOUND, "멤버십 카드를 찾을 수 없습니다."),
    ONLY_PHYSICAL_CARD_COULD_REGISTER(BAD_REQUEST, "물리 카드만 등록할 수 있습니다."),
    ACCESS_DENIED(FORBIDDEN, "접근 권한이 없습니다."),

    COMPANY_ALREADY_EXISTS(CONFLICT, "이미 존재하는 회사입니다."),
    COMPANY_NOT_FOUND(NOT_FOUND, "회사를 찾을 수 없습니다."),
    COMPANY_ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 회사입니다."),

    STATION_ALREADY_EXISTS(CONFLICT, "이미 존재하는 충전소입니다."),
    STATION_NOT_FOUND(NOT_FOUND, "충전소를 찾을 수 없습니다."),
    STATION_ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 충전소입니다."),

    CHARGER_ALREADY_EXISTS(CONFLICT, "이미 존재하는 충전기입니다."),
    CHARGER_NOT_FOUND(NOT_FOUND, "충전기를 찾을 수 없습니다."),
    CHARGER_ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 충전기입니다."),

    PAYMENT_METHOD_NOT_FOUND(NOT_FOUND, "결제 수단을 찾을 수 없습니다."),

    EMAIL_ALREADY_EXISTS(CONFLICT, "중복되는 메일이 존재합니다."),

    EMAIL_VERIFICATION_NOT_FOUND(NOT_FOUND, "이메일 인증 정보를 찾을 수 없습니다."),

    ALREADY_EMAIL_VERIFIED(BAD_REQUEST, "인증이 완료된 요청입니다."),
    EMAIL_VERIFICATION_EXPIRED(BAD_REQUEST, "인증 시간이 만료됐습니다. 인증 메일을 재발송하세요."),
    EMAIL_VERIFICATION_RESEND_TIME_EXCEED(BAD_REQUEST, "인증 메일 재전송 가능시간을 초과했습니다. 잠시후 다시 시도하세요."),
    ARGUMENT_NOT_VALID(BAD_REQUEST, "잘못된 입력입니다."),

    REGISTER_PAYMENT_FAILED(INTERNAL_SERVER_ERROR, "결제 수단 등록에 실패했습니다."),
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
