package com.zerobase.zbcharger.domain.payment.api.naver.dto.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 네이버페이 API 응답 코드
 */
@Getter
@RequiredArgsConstructor
public enum NaverPayApiResponseCode {

    /**
     * 성공
     */
    SUCCESS("Success"),

    /**
     * 유효하지 않은 가맹점
     */
    INVALID_MERCHANT("InvalidMerchant"),

    /**
     * 서비스 점검중
     */
    MAINTENANCE_ONGOING("MaintenanceOngoing");

    private final String value;
}
