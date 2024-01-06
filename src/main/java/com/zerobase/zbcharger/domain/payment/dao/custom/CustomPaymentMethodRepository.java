package com.zerobase.zbcharger.domain.payment.dao.custom;

import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import java.util.Optional;

public interface CustomPaymentMethodRepository {

    Optional<PaymentMethod> findPrimaryPaymentMethodByMemberId(Long memberId);
}
