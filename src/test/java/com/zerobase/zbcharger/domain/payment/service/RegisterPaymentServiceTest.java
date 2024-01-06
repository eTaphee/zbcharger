package com.zerobase.zbcharger.domain.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zerobase.zbcharger.domain.payment.dao.PaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.entity.NaverPay;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.entity.SmartroPay;
import com.zerobase.zbcharger.domain.payment.service.naver.RegisterNaverPayService;
import com.zerobase.zbcharger.domain.payment.service.smartro.RegisterSmartroPayService;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.HashMap;
import java.util.Map;
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

        Map<String, String> params = new HashMap<>();
        params.put("resultCode", "Success");
        params.put("reserveId", reserveId);
        params.put("tempReceiptId", "tempReceiptId");

        // when
        registerNaverPayService.registerPaymentMethod(params);
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
        Map<String, String> params = new HashMap<>();
        params.put("resultCode", "fail");

        // when
        // then
        assertThrows(CustomException.class,
            () -> registerNaverPayService.registerPaymentMethod(params));
    }

    @Test
    @DisplayName("스마트로 페이 정기/반복결제 등록 성공")
    @Transactional
    void successRegisterSmartroPaymentMethod() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("ResultCode", "3001");
        params.put("ResultMsg", "성공");
        params.put("PayMethod", "CARD");
        params.put("Mid", "ZBCharger");
        params.put("BuyerName", "Lee");
        params.put("Moid", "orderid");
        params.put("Tid", "transactionid");
        params.put("BillTokenKey", "OdoUbgo4EaqxLN9PtmPHIw==");
        params.put("MallUserId", "0");
        params.put("MallReserved", "MallReserved");
        params.put("VerifyValue", "oRdbWkxpttFSESFuN4C329YWuvvLjBGwmW6XgUbo4ts=");
        params.put("IssuerCardCd", "11");
        params.put("IssuerCardNm", "땡땡카드");
        params.put("DisplayCardNo", "5239-****-****-9001");
        params.put("CardExpire", "205012312400");
        params.put("EncodingType", "UTF8");
        params.put("RtnUrlEncUse", "Y");

        // when
        registerSmartroPayService.registerPaymentMethod(params);
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
        Map<String, String> params = new HashMap<>();
        params.put("ResultCode", "1001");
        params.put("ResultMsg", "성공");
        params.put("PayMethod", "CARD");
        params.put("Mid", "ZBCharger");
        params.put("BuyerName", "Lee");
        params.put("Moid", "orderid");
        params.put("Tid", "transactionid");
        params.put("BillTokenKey", "OdoUbgo4EaqxLN9PtmPHIw==");
        params.put("MallUserId", "0");
        params.put("MallReserved", "MallReserved");
        params.put("VerifyValue", "oRdbWkxpttFSESFuN4C329YWuvvLjBGwmW6XgUbo4ts=");
        params.put("IssuerCardCd", "11");
        params.put("IssuerCardNm", "땡땡카드");
        params.put("DisplayCardNo", "5239-****-****-9001");
        params.put("CardExpire", "205012312400");
        params.put("EncodingType", "UTF8");
        params.put("RtnUrlEncUse", "Y");

        // when
        // then
        assertThrows(CustomException.class,
            () -> registerSmartroPayService.registerPaymentMethod(params));
    }
}