package com.spring.visti.utils.urlshortener;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UrlShortener {

    // 해시와 원래 URL을 매핑하기 위한 저장소
    private static final Map<Integer, String> store = new HashMap<>();

    public static String shorten(String longUrl) {
        int hashCode = longUrl.hashCode();
        String encoded = Base62Util.encoding(hashCode);
        store.put(hashCode, longUrl);
        return encoded;
    }

    public static String expand(String shortUrl) {
        int decodedHashCode = Base62Util.decoding(shortUrl);
        return store.get(decodedHashCode);
    }
}