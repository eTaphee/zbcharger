package com.zerobase.zbcharger.domain.payment.service.smartro;

import com.zerobase.zbcharger.domain.payment.dao.PaymentMethodRepository;
import com.zerobase.zbcharger.domain.payment.dto.SmartroPayCallback;
import com.zerobase.zbcharger.domain.payment.entity.PaymentMethod;
import com.zerobase.zbcharger.domain.payment.entity.SmartroPay;
import com.zerobase.zbcharger.domain.payment.exception.RegisterPaymentException;
import com.zerobase.zbcharger.domain.payment.service.RegisterPaymentService;
import com.zerobase.zbcharger.util.AesUtils;
import com.zerobase.zbcharger.util.ShaUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 스마트로 페이 빌링 키 발급 서비스
 */
@Slf4j
@Service
public class RegisterSmartroPayService extends RegisterPaymentService<SmartroPayCallback> {

    private static final String SUCCESS = "3001";

    @Value("${payment.smartro.merchant-key}")
    private String merchantKey;

    public RegisterSmartroPayService(PaymentMethodRepository paymentMethodRepository) {
        super(paymentMethodRepository);
    }

    @Override
    protected PaymentMethod createPaymentMethod(SmartroPayCallback callback) {
        isSuccessResult(callback);

        String billTokenKey = decodeBillTokenKey(callback.getBillTokenKey());
        String verifyValue = generateVerifyValue(billTokenKey, callback);
        verifyForgery(verifyValue, callback.getVerifyValue());

        return SmartroPay.builder()
            .memberId(callback.getMemberId())
            .orderId(callback.getOrderId())
            .transactionId(callback.getTransactionId())
            .billTokenKey(billTokenKey)
            .issuerCardCode(callback.getIssuerCardCode())
            .issuerCardName(callback.getIssuerCardName())
            .displayCardNo(callback.getDisplayCardNumber())
            .cardExpire(callback.getCardExpire())
            .build();
    }

    /**
     * 콜백 결과 확인
     *
     * @param callback 콜백 파라미터
     */
    private void isSuccessResult(SmartroPayCallback callback) {
        if (!SUCCESS.equals(callback.getResultCode())) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("resultCode", callback.getResultCode());
            payload.put("resultMessage", callback.getResultMessage());
            throw new RegisterPaymentException("빌링키 발급 요청 실패", payload);
        }
    }

    /**
     * 빌링키 복호화
     *
     * @param billTokenKey 암호화된 빌링키
     * @return 복호화된 빌링키
     */
    private String decodeBillTokenKey(String billTokenKey) {
        String mKey = merchantKey.substring(0, 32);
        try {
            return AesUtils.decrypt(billTokenKey, mKey);
        } catch (Exception e) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("billTokenKey", billTokenKey);
            payload.put("merchantKey", merchantKey);
            throw new RegisterPaymentException("빌링키 복호화 실패", e, payload);
        }
    }

    /**
     * 콜백 위변조 검증
     *
     * @param generatedVerifiedValue 생성된 위변조 값
     * @param callbackVerifyValue    콜백에서 전달받은 위변조 값
     */
    private void verifyForgery(String generatedVerifiedValue, String callbackVerifyValue) {
        if (!generatedVerifiedValue.equals(callbackVerifyValue)) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("generatedVerifiedValue", generatedVerifiedValue);
            payload.put("callbackVerifyValue", callbackVerifyValue);
            throw new RegisterPaymentException("위변조 검증 실패", payload);
        }
    }

    /**
     * 위변조 검증 값 생성
     *
     * @param billTokenKey 복호화된 빌링키
     * @param callback     콜백 파라미터
     * @return 생성된 위변조 값
     */
    private String generateVerifyValue(String billTokenKey, SmartroPayCallback callback) {
        try {
            return ShaUtils.sha256Base64(
                callback.getMerchantId() + billTokenKey + callback.getDisplayCardNumber()
                    + callback.getResultCode() + "SMARTRO!@#");
        } catch (Exception e) {
            throw new RegisterPaymentException("위변조 검증 값 생성 실패", e);
        }
    }

}
