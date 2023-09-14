package com.spring.visti.global.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtProvideService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveRefreshToken(String email, String refreshToken, Date expireTime) {
        long expiresInMinutes = calculateExpirationInMinutes(expireTime);

        // expiresInMinutes 값이 0 또는 음수인 경우, Redis에 토큰을 저장하지 않습니다.
        if (expiresInMinutes > 0) {
            redisTemplate.opsForValue().set(email, refreshToken, expiresInMinutes, TimeUnit.MINUTES);
        }
    }

    public String getRefreshToken(String email){
        return (String) redisTemplate.opsForValue().get(email);
    }

    public String expireRefreshToekn(String email){
        return (String) redisTemplate.opsForValue().get(email);
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
