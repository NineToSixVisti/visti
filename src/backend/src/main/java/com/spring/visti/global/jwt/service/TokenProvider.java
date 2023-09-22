package com.spring.visti.global.jwt.service;

import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.global.redis.service.JwtProvideService;
import com.spring.visti.utils.exception.ApiException;
import com.spring.visti.domain.member.service.CustomUserDetailsService;
import com.spring.visti.global.jwt.constant.GrantType;
import com.spring.visti.global.jwt.constant.TokenType;
import com.spring.visti.global.jwt.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;



import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.spring.visti.utils.exception.ErrorCode.*;

@Slf4j
@Component
//@RequiredArgsConstructor // 의존성 주입의 관점에서는 제거해도 상관없다...?
public class TokenProvider  {

    private final JwtProvideService jwtProvideService;
    private final CustomUserDetailsService userDetailsService;
    private final Key key;
    private static final Long ACCESS_TIME = 30 * 60 * 1000L; // 30 min
//    private static final Long ACCESS_TIME = 20 * 1000L;
//    private static final Long REFRESH_TIME = 60 * 1000L; // 1week

    private static final Long REFRESH_TIME = 7 * 24 * 60 * 60 * 1000L; // 1week

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_EMAIL = "user_email";
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";

    public TokenProvider(@Value("${jwt.secret.key}") String secretKey,
                         JwtProvideService jwtProvideService, CustomUserDetailsService userDetailsService){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
        this.jwtProvideService = jwtProvideService;
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String email){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String createAccessToken(String authorities, String email, Date accessTokenExpiresIn){
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USER_EMAIL, email)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(Date refreshTokenExpiresIn){
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public TokenDTO generateTokenDTO(Authentication authentication, Role role){
        //권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String email = authentication.getName();

        long now = (new Date()).getTime();

        long expiryTimeRefresh = now + REFRESH_TIME;
        long expiryTimeAccess = now + ACCESS_TIME;
        if (Role.ADMIN.equals(role)){
            expiryTimeRefresh = expiryTimeRefresh + REFRESH_TIME * 30;
            expiryTimeAccess = expiryTimeAccess + REFRESH_TIME;
        }
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(expiryTimeAccess);
        String accessToken = createAccessToken(authorities, email, accessTokenExpiresIn);
//        TokenProvider.setHeaderAccessToken(response, accessToken);
//        TokenProvider.setHeaderRefreshToken(response, refreshToken);
        // Refresh Token 생성

        Date refreshTokenExpiresIn = new Date(expiryTimeRefresh);
        String refreshToken = createRefreshToken(refreshTokenExpiresIn);

        return TokenDTO.builder()
                .grantType(GrantType.BEARER)
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpiresIn)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpiresIn)
                .build();
    }

    public void validateToken(String token){

        try{
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            if (TokenType.ACCESS.name().equals(claims.getSubject()) && jwtProvideService.isTokenInBlackList(token)) {
                throw new ApiException(JWT_INVALID);
            }

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new ApiException(JWT_INVALID);
        } catch (ExpiredJwtException e) {
            throw new ApiException(JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new ApiException(JWT_NOT_SUPPORT);
        } catch (IllegalArgumentException e) {
            throw new ApiException(JWT_ERROR);
        }
    }

    public Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }



    public void setHeaderAccessToken(HttpServletResponse response, String accessToken){
        response.setHeader("Access_Token", accessToken);
    }

    // Refresh Token Header set
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken){
        response.setHeader("Refresh_Token", refreshToken);
    }

    // get Header Token
    public String getHeaderToken(HttpServletRequest request, String type){
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
        if(claims.get(AUTHORITIES_KEY) == null){
            throw new ApiException(NO_AUTHORIZE);
        }


        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User((String) claims.get(USER_EMAIL), "", authorities);
//        System.out.println("프린시펄 확인" + principal + "\n"+authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }

    public String issueNewAccessToken(Authentication authentication) {
        //권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
//        System.out.println("====="+authorities);
        String email = authentication.getName();

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TIME);
        return createAccessToken(authorities, email, accessTokenExpiresIn);
    }
    
// 이후 레디스 추가시 수행하는 것이 적절할 듯z
//    public void expireToken(String token){
//        Claims claims = parseClaims(token);
//
//        if (claims.get(AUTHORITIES_KEY) != null){
//
//        }
//    }
}





