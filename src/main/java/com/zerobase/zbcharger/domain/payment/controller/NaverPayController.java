package com.zerobase.zbcharger.domain.payment.controller;

import com.zerobase.zbcharger.domain.payment.service.naver.RegisterNaverPayService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverPayController {

    private final RegisterNaverPayService registerNaverPayService;

    /**
     * 정기/반복 결제 등록 콜백
     */
    @GetMapping("payment/naverpay/register-recurrent-payment-callback")
    public void registerRecurrentPaymentCallback(@RequestParam Map<String, String> params) {
        registerNaverPayService.registerPaymentMethod(params);
    }
}
