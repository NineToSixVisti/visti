package com.spring.visti.utils.urlutils;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

@Component
public class SecurePathUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = "L3g4T2s9H7m5R1qW".getBytes();
    private static final String key="k5H2e9Q6w1Y4z7X0";

    public String encryptAndEncode(String value) throws Exception {
        byte[] encrypted = encrypt(value);
        return Base62Util.encoding(bytesToLong(encrypted));
    }

    public String decodeAndDecrypt(String encodedValue) throws Exception {
        long decodedLong = Base62Util.decoding(encodedValue);
        byte[] decrypted = decrypt(longToBytes(decodedLong));
        return new String(decrypted).trim();  // 패딩을 제거
    }


    private byte[] encrypt(String value) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(IV);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Arrays.copyOf(encrypted, 8);  // 16바이트 중 앞의 8바이트만 반환
    }

    private byte[] decrypt(byte[] value) throws Exception {
        byte[] paddedValue = new byte[16];  // 16바이트 크기로 초기화
        System.arraycopy(value, 0, paddedValue, 0, value.length);  // 원본 8바이트 값을 앞쪽에 복사
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(IV);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        return cipher.doFinal(paddedValue);
    }

    // 바이트 배열을 롱 값으로 변환하는 유틸리티 (id 는 Long 이기 때문에 8Byte 이고, 이에 8자리만 챙김)
    private long bytesToLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length && i < 8; i++) {
            value = (value << 8) + (bytes[i] & 0xff);
        }
        return value;
    }

    // 롱 값을 바이트 배열로 변환하는 유틸리티 (id 는 Long 이기 때문에 8Byte 이고, 이에 8자리만 챙김)
    private byte[] longToBytes(long value) {
        byte[] bytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bytes[i] = (byte)(value & 0xff);
            value >>= 8;
        }
        return bytes;
    }
}
