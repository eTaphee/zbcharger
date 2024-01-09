package com.zerobase.zbcharger.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShaUtilsTest {

    @Test
    @DisplayName("SHA256 BASE64")
    void SHA256_BASE64() throws Exception {
        System.out.println(ShaUtils.sha256Base64("ZBChargeruser1billingkey5239-****-****-90013001SMARTRO!@#"));
    }
}