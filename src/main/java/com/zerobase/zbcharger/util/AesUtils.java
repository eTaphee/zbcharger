package com.zerobase.zbcharger.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jodd.util.Base64;

public final class AesUtils {

    private final static byte[] IV_BYTES = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private final static AlgorithmParameterSpec IV_SPEC = new IvParameterSpec(IV_BYTES);

    public static String encrypt(String plainText, String key) throws Exception {
        byte[] textBytes = plainText.getBytes(UTF_8);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes(UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, IV_SPEC);
        return Base64.encodeToString(cipher.doFinal(textBytes));
    }

    public static String decrypt(String cipherText, String key) throws Exception {
        byte[] textBytes = Base64.decode(cipherText);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes(UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, IV_SPEC);
        return new String(cipher.doFinal(textBytes), UTF_8);
    }
}
