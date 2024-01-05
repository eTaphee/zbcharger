package com.zerobase.zbcharger.domain.payment.dto;

import lombok.Getter;

/**
 * 결제 콜백 공통 DTO
 */
@Getter
public abstract class RegisterPaymentCallback {

    /**
     * 회원 아이디
     */
    private final Long memberId;

    protected RegisterPaymentCallback(Long memberId) {
        this.memberId = memberId;
    }
}
