package com.spring.demo.external.oauth.controller;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.external.oauth.service.OAuthService;
import com.spring.demo.external.oauth.service.OAuthServiceFactory;
import com.spring.demo.global.jwt.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthServiceFactory serviceFactory;

    @GetMapping("/oauth/{provider}")
    public ResponseEntity<? extends BaseResponseDTO<TokenDTO>> OAuthLoginService(
            @PathVariable String provider,
            @RequestParam Map<String, String> params
    ){

        OAuthService service = serviceFactory.getService(provider);
        BaseResponseDTO<TokenDTO> response = service.processOAuthLogin(params);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
