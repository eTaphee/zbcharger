package com.zerobase.zbcharger.domain.payment.dao;

import com.zerobase.zbcharger.domain.payment.dao.custom.CustomPaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>,
    CustomPaymentMethodRepository {

    Long countByMemberId(Long memberId);
}
