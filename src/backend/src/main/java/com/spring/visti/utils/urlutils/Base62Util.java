package com.spring.visti.utils.urlutils;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;

@Component
public class Base62Util {

    static final char[] BASE62 = "bZ3Egr2JwYn6TkmaQ8zsNlOj9VdptW7v0iDLyxXheR5oPFUM4S1fIBGKcCAbuHq".toCharArray();
//    static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    public static String encoding(long value) {
        final StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (value % 62);
            sb.append(BASE62[i]);
            value /= 62;
        } while (value > 0);
        return sb.toString();
    }

    public static int decoding(String value) {
        int result=0;
        int power=1;
        for (int i = 0; i < value.length(); i++) {
            int digit = new String(BASE62).indexOf(value.charAt(i));
            result += digit * power;
            power *= 62;
        }
        return result;
    }

}
