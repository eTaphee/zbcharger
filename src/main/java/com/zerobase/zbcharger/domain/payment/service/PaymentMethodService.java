package com.zerobase.zbcharger.domain.payment.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.ACCESS_DENIED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.PAYMENT_METHOD_NOT_FOUND;

import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.payment.dao.PaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    /**
     * 기본 결제수단으로 설정
     *
     * @param id     결제수단 아이디
     * @param member 회원
     */
    @Transactional
    public void setPrimaryPaymentMethod(Long id, Member member) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
            .orElseThrow(() -> new CustomException(PAYMENT_METHOD_NOT_FOUND));

        if (!Objects.equals(paymentMethod.getMemberId(), member.getId())) {
            throw new CustomException(ACCESS_DENIED);
        }

        // TODO: update 시 왜 member_id가 set 되는지 확인 필요
        paymentMethodRepository.findPrimaryPaymentMethodByMemberId(member.getId())
            .ifPresent(PaymentMethod::unsetPrimary);

        paymentMethod.setPrimary();
    }
}
