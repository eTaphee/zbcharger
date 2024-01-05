package com.zerobase.zbcharger.domain.payment.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.REGISTER_PAYMENT_FAILED;

import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.exception.RegisterPaymentException;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 콜백 기반 결제 수단 등록 서비스
 *
 * @param <Callback> 콜백 파라미터 타입
 */
@Slf4j
public abstract class RegisterPaymentService<Callback> {

    /**
     * 결제 수단 등록
     *
     * @param params 콜백 파라미터
     */
    @Transactional
    public void registerPaymentMethod(Map<String, String> params) {
        Callback callback = toPaymentCallback(params);

        try {
            registerInternal(callback);
        } catch (RegisterPaymentException e) {
            log.error("결제 수단 등록 실패: {}, {}", e.getMessage(), e.getPayloadKeyValues(), e);
            throw new CustomException(REGISTER_PAYMENT_FAILED, e);
        }
    }

    /**
     * 벤더별 결제 수단 등록 로직
     *
     * @param callback 콜백 파라미터
     * @return 결제 수단
     */
    protected abstract PaymentMethod registerInternal(Callback callback);

    /**
     * 콜백 파라미터를 콜백 타입으로 변환
     *
     * @param params 콜백 파라미터
     * @return 콜백 타입
     */
    protected abstract Callback toPaymentCallback(Map<String, String> params);
}
