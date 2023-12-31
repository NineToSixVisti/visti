package com.spring.visti.global.config;

import com.spring.visti.domain.member.service.CustomUserDetailsService;
import com.spring.visti.global.jwt.service.TokenAuthFilter;
import com.spring.visti.global.redis.service.JwtProvideService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.visti.global.jwt.service.TokenProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvideService jwtProvideService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring()
//                    .requestMatchers(toH2Console())
                    .requestMatchers(Arrays.stream(WHITELIST)
                            .map(AntPathRequestMatcher::new)
                            .toArray(RequestMatcher[]::new));
        };
    }


    public SecurityConfig(
            TokenProvider tokenProvider, JwtProvideService jwtProvideService,
            CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvideService = jwtProvideService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       log.info("========================== 우선순위 확인 1======================");
        // Filter
        http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable) // csrf disable
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers(antMatcher("/api/member/signin")).permitAll()
                .requestMatchers(antMatcher("/api/member/inform")).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new TokenAuthFilter(tokenProvider, jwtProvideService), UsernamePasswordAuthenticationFilter.class);


//        // 기본 제거
//        http.httpBasic(withDefaults());

//         cors header access
//        http.cors().configurationSource(corsConfigurationSource());


        // STATELESS
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }



//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    private static final String[] WHITELIST = {
            // for test h2
            "/h2-console/**",

            // Send Mail
            "/api/member/sendmail",

//            // url validate
//            "/short/*",
            // Story-Box validate
            "/api/story-box/validate",

            // Login
            "/api/member/signin",
            "/api/member/signup",
            "/api/member/verify-member",
            "/api/member/verify-authnum",
            "/oauth/**",

            // Swagger
            "/api/v3/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };
}
