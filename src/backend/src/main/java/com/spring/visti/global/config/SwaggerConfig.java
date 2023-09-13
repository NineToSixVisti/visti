package com.spring.visti.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * Swagger springdoc-ui 구성 파일
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("데모 프로젝트 API Document")
                .version("v0.0.1")
                .description("데모 프로젝트의 API 명세서입니다.");

        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        securityRequirements.add(new SecurityRequirement().addList("Access_Token"));

        Components components = new Components()
                .addSecuritySchemes("Access_Token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("access_token")
                                .description("토큰 앞에 : 'Bearer '를 작성해주세요"));


        return new OpenAPI()
                .components(components)
                .info(info)
                .security(securityRequirements);
// 지금은 Swagger 접근을 모든 사람이 할 수 있게 해뒀는데, 이 부분은 이후 관리자나 우회하기 위해서는 apikey를 등록해서 사용해야한다.
//        SecurityScheme apiKey = new SecurityScheme()
//                .type(SecurityScheme.Type.APIKEY)
//                .in(SecurityScheme.In.HEADER)
//                .name("X-API-KEY")
//                .description("Custom header that APIs consume to authenticate");


//        return new OpenAPI()
//                .components(new Components().addSecuritySchemes("api_key", apiKey))
//                .info(info);
    }
}