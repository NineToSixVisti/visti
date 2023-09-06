package com.spring.visti.api.member.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import jakarta.mail.MessagingException;

public interface EmailService {
        BaseResponseDTO<String> sendMail(String email, String type) throws MessagingException;
}
