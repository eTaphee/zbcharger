package com.zerobase.zbcharger.domain.payment.exception;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RegisterPaymentException extends RuntimeException {

    private final Map<String, Object> payload;

    public RegisterPaymentException(String message, Map<String, Object> payload) {
        super(message);
        this.payload = payload;
    }

    public RegisterPaymentException(String message, Throwable cause, Map<String, Object> payload) {
        super(message, cause);
        this.payload = payload;
    }

    public RegisterPaymentException(String message, Throwable cause) {
        super(message, cause);
        this.payload = Collections.emptyMap();
    }

    public String getPayloadKeyValues() {
        return payload.entrySet()
            .stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining(" "));
    }
}
