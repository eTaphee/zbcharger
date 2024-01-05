package com.zerobase.zbcharger.domain.payment.api.naver.dto;

import static com.zerobase.zbcharger.domain.payment.api.naver.dto.constant.NaverPayApiResponseCode.SUCCESS;

/**
 * 네이버페이 API 응답
 *
 * @param <T>     바디 타입
 * @param code    응답 코드
 * @param message 응답 메시지
 * @param body    응답 바디
 */
public record NaverPayResponse<T>(String code, String message, T body) {

    public boolean isSuccess() {
        return SUCCESS.getValue().equals(code);
    }
}
