package com.spring.demo.api.member.service;

import com.spring.demo.api.dto.BaseResponseDTO;
import jakarta.mail.MessagingException;

public interface EmailService {
        BaseResponseDTO<String> sendMail(String email, String type) throws MessagingException;
}
