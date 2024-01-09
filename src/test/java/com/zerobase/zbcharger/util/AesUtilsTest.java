package com.zerobase.zbcharger.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AesUtilsTest {

    @Test
    @DisplayName("빌링키 암호화")
    void 빌링키_암호화() {
        try {
            System.out.println(
                AesUtils.encrypt("user1billingkey",
                    "abcdefghijklmnopqrstuvwxyz1234567890".substring(0, 32)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}