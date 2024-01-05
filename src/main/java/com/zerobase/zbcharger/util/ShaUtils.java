package com.zerobase.zbcharger.util;

import java.security.MessageDigest;
import org.apache.tomcat.util.codec.binary.Base64;

public final class ShaUtils {

    public static String sha256Base64(String plainText) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(plainText.getBytes());
        return Base64.encodeBase64String(messageDigest.digest());
    }
}
