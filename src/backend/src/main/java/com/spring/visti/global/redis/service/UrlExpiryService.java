package com.spring.visti.global.redis.service;

import com.spring.visti.utils.urlutils.Base62Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UrlExpiryService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String shorten(String longUrl) {

        int hashCode = longUrl.hashCode();
        long longHashCode = ((long) hashCode) & 0xFFFFFFFFL;

        String shortUrl = Base62Util.encoding(longHashCode);


        saveStoryBoxUrlToken(shortUrl, longUrl);
        return shortUrl;
    }

    public void removePreviousUrl(String longUrl){
        int hashCode = longUrl.hashCode();
        long longHashCode = ((long) hashCode) & 0xFFFFFFFFL;

        String shortUrl = Base62Util.encoding(longHashCode);
        redisTemplate.delete(shortUrl);
    }

    public String expand(String shortUrl) {
        return (String) redisTemplate.opsForValue().get(shortUrl);
    }

    // 인증 코드 저장
    private void saveStoryBoxUrlToken(String StoryBoxShortUrl, String StoryBoxUrlToken) {
        redisTemplate.opsForValue().set(StoryBoxShortUrl, StoryBoxUrlToken, 24, TimeUnit.HOURS); // 24시간 동안 저장됨
    }
}
