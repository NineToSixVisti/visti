package com.spring.visti.global.jwt.service;

import com.spring.visti.domain.member.service.CustomUserDetails;
import com.spring.visti.global.redis.service.JwtProvideService;
import com.spring.visti.utils.exception.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.spring.visti.utils.exception.ErrorCode.*;


@Slf4j
public class TokenAuthFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final JwtProvideService jwtProvideService;

    public TokenAuthFilter(TokenProvider tokenProvider,JwtProvideService jwtProvideService) {
        this.jwtProvideService=  jwtProvideService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException{


        log.info("JWT Filtering Started! =======================================");
        String accessToken = null;
        String email = null;
        try {
            accessToken = extractTokenFromHeader(tokenProvider.getHeaderToken(request, "Access"));
            email = (String) tokenProvider.parseClaims(accessToken).get("user_email");
            setAuthentication(email);


            // 액세스 토큰의 유효성 검사
            tokenProvider.validateToken(accessToken);

            log.info("==== Access Token alive! ===================================");
        } catch (ApiException e) {
            if (e.getCode().equals(JWT_EXPIRED)) {
                // 액세스 토큰이 만료되었을 경우 리프레시 토큰 검증
                String refreshToken = getMemberRefreshToken();
                if (refreshToken == null){ throw new ApiException(NO_TOKEN_HEADER);}

                try {
                    log.info("==== Access Token Died, is Refresh valid? ==================");
                    tokenProvider.validateToken(refreshToken);

                    // 리프레시 토큰이 유효하면 새로운 액세스 토큰 발급
                    Authentication newAuthentication = tokenProvider.getAuthentication(accessToken);
                    String newAccessToken = tokenProvider.issueNewAccessToken(newAuthentication);

                    // 새로운 엑세스 토큰 인증 진행
                    setAuthentication(email);
                    tokenProvider.setHeaderAccessToken(response, newAccessToken);
                    log.info("==== Access Token Issued! ===================================");
                    chain.doFilter(request, response);
                    return;
                } catch (ApiException ex) {
                    // 리프레시 토큰도 유효하지 않으면 예외 처리
                    log.info("==== Refresh Token Expired! ===================================");
                    throw new ApiException(JWT_EXPIRED);
                }
            } else {
                log.info("accessToken@@@@@@@@@@@@@@" + accessToken);
                // 액세스 토큰이 유효하지 않고 만료되지 않았을 경우 (예: 변조된 토큰)
                throw new ApiException(JWT_NOT_SUPPORT);
            }
        }

        log.info("JWT Filtering Finished! =======================================");

        chain.doFilter(request, response);
    }

    // SecurityContext에 Authentication 객체 저장
    private void setAuthentication(String email){
        Authentication authentication = tokenProvider.createAuthentication(email);
        // security가 securitycontextHolder에서 인증 객체 확인
        // TokenAuthfilter에서 authentication을 넣어주면 UsernamePasswordAuthenticationFilter 내에서 인증 된 것을 확인하고 추가적인 작업을 진행하지 않는다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private static final String BEARER_PREFIX = "Bearer ";

    public String extractTokenFromHeader(String headerValue) {
        if (headerValue == null) {
            throw new ApiException(NO_TOKEN_HEADER);
        }

        if (headerValue.trim().toLowerCase().startsWith(BEARER_PREFIX.toLowerCase())) {
            return headerValue.trim().substring(BEARER_PREFIX.length()).trim();
        }

        return headerValue.trim();
    }

    private String getMemberRefreshToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((CustomUserDetails) authentication.getPrincipal()).getMember().getRefreshToken();
        }
        throw new ApiException(JWT_NOT_SUPPORT);
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("/swagger-ui.html") || uri.contains("/v3/api-docs") || uri.contains("/swagger-ui/");
    }
}
