package com.zerobase.zbcharger.domain.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zerobase.zbcharger.domain.payment.dao.PaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.dto.NaverPayCallback;
import com.zerobase.zbcharger.domain.payment.dto.SmartroPayCallback;
import com.zerobase.zbcharger.domain.payment.entity.NaverPay;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.entity.SmartroPay;
import com.zerobase.zbcharger.domain.payment.service.naver.RegisterNaverPayService;
import com.zerobase.zbcharger.domain.payment.service.smartro.RegisterSmartroPayService;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.weaver.ast.Call;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RegisterPaymentServiceTest {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private RegisterNaverPayService registerNaverPayService;

    @Autowired
    private RegisterSmartroPayService registerSmartroPayService;

    @Test
    @DisplayName("네이버페이 정기/반복결제 등록 성공")
    @Transactional
    void successRegisterNaverPaymentMethod() {
        // given
        String reserveId = "reserveId";

        NaverPayCallback callback = NaverPayCallback.builder()
            .resultCode("Success")
            .reserveId(reserveId)
            .tempReceiptId("tempReceiptId")
            .build();

        // when
        registerNaverPayService.registerPaymentMethod(callback);
        PaymentMethod paymentMethod = paymentMethodRepository.findAll().get(0);

        // then
        assertInstanceOf(NaverPay.class, paymentMethod);
        assertEquals("RECURRENT_ID:reserveId", ((NaverPay) paymentMethod).getRecurrentId());
    }

    @Test
    @DisplayName("네이버페이 정기/반복결제 등록 실패")
    @Transactional
    void failRegisterNaverPaymentMethod() {
        // given
        NaverPayCallback callback = NaverPayCallback.builder()
            .resultCode("fail")
            .build();

        // when
        // then
        assertThrows(CustomException.class,
            () -> registerNaverPayService.registerPaymentMethod(callback));
    }

    @Test
    @DisplayName("스마트로 페이 정기/반복결제 등록 성공")
    @Transactional
    void successRegisterSmartroPaymentMethod() {
        // given
        SmartroPayCallback callback = SmartroPayCallback.builder()
            .resultCode("3001")
            .resultMessage("성공")
            .payMethod("CARD")
            .merchantId("ZBCharger")
            .payMethod("CARD")
            .orderId("orderid")
            .transactionId("transactionid")
            .billTokenKey("OdoUbgo4EaqxLN9PtmPHIw==")
            .mallUserId("0")
            .verifyValue("oRdbWkxpttFSESFuN4C329YWuvvLjBGwmW6XgUbo4ts=")
            .issuerCardCode("11")
            .issuerCardName("땡땡카드")
            .displayCardNumber("5239-****-****-9001")
            .encodingType("UTF8")
            .returnUrlEncodingUse(true)
            .build();

        // when
        registerSmartroPayService.registerPaymentMethod(callback);
        PaymentMethod paymentMethod = paymentMethodRepository.findAll().get(0);

        // then
        assertInstanceOf(SmartroPay.class, paymentMethod);
        assertEquals("user1billingkey", ((SmartroPay) paymentMethod).getBillTokenKey());
    }

    @Test
    @DisplayName("스마트로 페이 정기/반복결제 등록 실패")
    @Transactional
    void failRegisterSmartroPaymentMethod() {
        // given
        SmartroPayCallback callback = SmartroPayCallback.builder()
            .resultCode("1001")
            .mallUserId("0")
            .build();

        // when
        // then
        assertThrows(CustomException.class,
            () -> registerSmartroPayService.registerPaymentMethod(callback));
    }
}