package com.zerobase.zbcharger.domain.payment.dao.custom.impl;

import static com.zerobase.zbcharger.domain.payment.entity.QPaymentMethod.paymentMethod;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.zbcharger.domain.payment.dao.custom.CustomPaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomPaymentMethodRepositoryImpl implements CustomPaymentMethodRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PaymentMethod> findPrimaryPaymentMethodByMemberId(Long memberId) {
        return Optional.ofNullable(
            queryFactory.select(paymentMethod)
                .from(paymentMethod)
                .where(paymentMethod.memberId.eq(memberId)
                    .and(paymentMethod.isPrimary.eq(true)))
                .fetchOne());
    }
}
