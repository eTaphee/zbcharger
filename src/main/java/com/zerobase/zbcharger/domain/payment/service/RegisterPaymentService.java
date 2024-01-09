package com.zerobase.zbcharger.domain.payment.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.REGISTER_PAYMENT_FAILED;

import com.zerobase.zbcharger.domain.payment.dao.PaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.exception.RegisterPaymentException;
import com.zerobase.zbcharger.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 콜백 기반 결제 수단 등록 서비스
 *
 * @param <Callback> 콜백 파라미터 타입
 */
@Slf4j
@RequiredArgsConstructor
public abstract class RegisterPaymentService<Callback> {

    private final PaymentMethodRepository paymentMethodRepository;

    /**
     * 결제 수단 등록
     *
     * @param params 콜백 파라미터
     */
    @Transactional
    public void registerPaymentMethod(Callback callback) {
        PaymentMethod paymentMethod = null;
        try {
            // 벤더별 결제 수단 생성
            paymentMethod = createPaymentMethod(callback);
        } catch (RegisterPaymentException e) {
            log.error("결제 수단 등록 실패: {}, {}", e.getMessage(), e.getPayloadKeyValues(), e);
            throw new CustomException(REGISTER_PAYMENT_FAILED, e);
        }

        // 결제 수단이 없으면 기본 결제 수단으로 설정
        if (paymentMethodRepository.countByMemberId(paymentMethod.getMemberId()) == 0) {
            paymentMethod.setPrimary();
        }

        // 저장
        paymentMethodRepository.save(paymentMethod);
    }

    /**
     * 벤더별 결제 수단 생성 로직
     *
     * @param callback 콜백 파라미터
     * @return 결제 수단
     */
    protected abstract PaymentMethod createPaymentMethod(Callback callback);
}
