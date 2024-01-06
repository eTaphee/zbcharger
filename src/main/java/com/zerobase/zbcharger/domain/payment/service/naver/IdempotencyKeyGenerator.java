package com.zerobase.zbcharger.domain.payment.service.naver;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class IdempotencyKeyGenerator {

    public String getIdempotencyKey() {
        return UUID.randomUUID().toString();
    }
}
