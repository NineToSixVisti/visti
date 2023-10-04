package com.spring.visti.global.redis.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtProvideService {

    @Autowired
    private RedisTemplate<String, Object> redisBlackListTemplate;

    public void addToBlackList(String token, Date expireTime) {
        // 토큰의 만료 시간이 있으면 해당 만료 시간까지만 블랙리스트에 추가됩니다.
        long expiresInMinutes = calculateExpirationInMinutes(expireTime);

        if (expiresInMinutes > 0) {
            redisBlackListTemplate.opsForValue().set(token, "invalid", expiresInMinutes, TimeUnit.MINUTES);
        } else {
            // 토큰의 만료 시간이 없는 경우 임의의 기간 동안 블랙리스트에 추가됩니다.
            redisBlackListTemplate.opsForValue().set(token, "invalid", 30, TimeUnit.MINUTES);
        }
    }

    public boolean isTokenInBlackList(String token) {
        return redisBlackListTemplate.opsForValue().get(token) != null;
    }


    // 만료 시간을 계산하는 메서드
    private long calculateExpirationInMinutes(Date expireTime) {
        if (expireTime == null) {
            return 0; // 만료 시간이 null인 경우 토큰을 저장하지 않음
        }

        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = expireTime.getTime();

        if (expireTimeMillis <= currentTimeMillis) {
            return 0; // 이미 만료된 경우 토큰을 저장하지 않음
        }

        // 현재 시간과 만료 시간 간의 차이를 분 단위로 계산하여 반환
        return (expireTimeMillis - currentTimeMillis) / 60000;
    }
}
