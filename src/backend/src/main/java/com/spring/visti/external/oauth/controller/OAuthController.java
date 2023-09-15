package com.spring.visti.external.oauth.controller;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.external.oauth.service.OAuthService;
import com.spring.visti.external.oauth.service.OAuthServiceFactory;
import com.spring.visti.global.jwt.dto.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Tag(name = "Member 컨트롤러", description = "Member Controller API Document")
public class OAuthController {

    private final OAuthServiceFactory serviceFactory;

    @GetMapping("/oauth/{provider}")
    @Operation(summary = "OAuth 진행", description = "[네이버|카카오] 소셜 로그인 \n 네이버 : parmas= 'code', 'state' || 카카오 : params= 'code'")
    public ResponseEntity<? extends BaseResponseDTO<TokenDTO>> OAuthLoginService(
            @PathVariable String provider,
            @RequestParam Map<String, String> params
    ){

        OAuthService service = serviceFactory.getService(provider);
        BaseResponseDTO<TokenDTO> response = service.processOAuthLogin(params);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
