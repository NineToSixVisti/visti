package com.spring.demo.global.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 인증 코드 저장
    public void saveAuthCode(String email, String authCode) {
        redisTemplate.opsForValue().set(email, authCode, 10, TimeUnit.MINUTES); // 10분 동안 저장
    }

    // 인증 코드 조회
    public String getAuthCode(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }
}
