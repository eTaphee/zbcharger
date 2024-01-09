package com.zerobase.zbcharger.domain.payment.controller;

import com.zerobase.zbcharger.configuration.security.annotation.CurrentUser;
import com.zerobase.zbcharger.domain.member.entity.Member;
import com.zerobase.zbcharger.domain.payment.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping("/payments/{id}/set-primary")
    public void setPrimaryPaymentMethod(@PathVariable Long id, @CurrentUser Member member) {
        paymentMethodService.setPrimaryPaymentMethod(id, member);
    }
}
