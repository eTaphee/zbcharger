package com.zerobase.zbcharger.domain.payment.dto.resolver;

import com.zerobase.zbcharger.domain.payment.dto.SmartroPayCallback;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SmartroPayCallbackArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(SmartroPayCallback.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        return SmartroPayCallback.builder()
            .payMethod(webRequest.getParameter("PayMethod"))
            .merchantId(webRequest.getParameter("Mid"))
            .orderId(webRequest.getParameter("Moid"))
            .transactionId(webRequest.getParameter("Tid"))
            .billTokenKey(webRequest.getParameter("BillTokenKey"))
            .mallUserId(webRequest.getParameter("MallUserId"))
            .resultCode(webRequest.getParameter("ResultCode"))
            .resultMessage(webRequest.getParameter("ResultMsg"))
            .verifyValue(webRequest.getParameter("VerifyValue"))
            .issuerCardCode(webRequest.getParameter("IssuerCardCd"))
            .issuerCardName(webRequest.getParameter("IssuerCardNm"))
            .displayCardNumber(webRequest.getParameter("DisplayCardNo"))
            .cardExpire(LocalDateTime.parse(
                Objects.requireNonNull(webRequest.getParameter("CardExpire")),
                DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
            .encodingType(webRequest.getParameter("EncodingType"))
            .returnUrlEncodingUse("Y".equals(webRequest.getParameter("RtnUrlEncUse")))
            .build();
    }
}
