package com.zerobase.zbcharger.domain.payment.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.REGISTER_PAYMENT_FAILED;

import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.exception.RegisterPaymentException;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class RegisterPaymentService<Callback> {

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

    protected abstract PaymentMethod registerInternal(Callback callback);

    protected abstract Callback toPaymentCallback(Map<String, String> params);
}
