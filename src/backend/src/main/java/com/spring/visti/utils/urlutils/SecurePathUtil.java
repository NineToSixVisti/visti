package com.spring.visti.utils.urlutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

@Component
public class SecurePathUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    @Value("${secure.key}")
    private String keyString;
    @Value("${secure.iv}")
    private String ivString;

    private static byte[] IV;
    private static String key;

    @PostConstruct
    public void init() {
        IV = ivString.getBytes();
        key = keyString;
    }

    public static String encryptAndEncode(String value) {
        try{
            byte[] encryptedValue = encrypt(value);
            return encodingToString(encryptedValue);
        }catch (Exception e){
            throw new RuntimeException("Encryption failed!", e);
        }
    }

    public static String decodeAndDecrypt(String encodedValue) {
        try {
            byte[] decryptedValue = decodingToBytes(encodedValue);
            return new String(decrypt(decryptedValue));
        }catch (Exception e){
            throw new RuntimeException("Encryption failed!", e);
        }
    }


    private static byte[] encrypt(String value) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(IV);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(value.getBytes());
    }

    private static byte[] decrypt(byte[] value) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(IV);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        return cipher.doFinal(value);
    }

    private static String encodingToString(byte[] value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value);
    }

    private static byte[] decodingToBytes(String value) {
        return Base64.getUrlDecoder().decode(value);
    }

}
