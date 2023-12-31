package com.zerobase.zbcharger.domain.payment.controller;

import com.zerobase.zbcharger.domain.payment.dto.SmartroPayCallback;
import com.zerobase.zbcharger.domain.payment.service.smartro.RegisterSmartroPayService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 스마트로 페이 연동 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class SmartroPayController {

    private final RegisterSmartroPayService registerSmartroPayService;

    /**
     * 스마트로 페이 빌링 키 발급 콜백
     */
    @PostMapping("payment/smartropay/issue-billing-callback")
    public void issueBillingKeyCallback(SmartroPayCallback callback) {
        registerSmartroPayService.registerPaymentMethod(callback);
        // TODO: redirect
    }
}
