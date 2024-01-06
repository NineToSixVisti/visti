# Backend 부분 상세


## 0. 팀원 🧑‍🤝‍🧑

*1차. 23.08.14 ~ 23.10.06 (6주)*
|팀원|맡은 파트|
|:--:|:--:|
|**김수찬**| 백엔드 서비스 생성 및 API 최적화|
|**정예원**| 검색기능 구현 및 서버 배포 |

*2차. 23.12.14 ~ (진행중)*

### 개발환경 🛠️
>
> Java 17.
> >
> Spring Boot 3.1
> > 
> Redis 3.0
> > 
> MySQL 8.x

# 2. 상세 업무 내역

## 2.1 로그인 기능

## 2.2 검색기능

## 2.3 페이징

## 2.4 암호화

## 2.5 부하테스트
 

# x. Ignore 처리된 properties 입니다. 해당 부분을 가져가서 서비스에 적용해주세요

## x.1. application.properties

```
# MySQL
spring.datasource.url={your db}
spring.datasource.username={your username}
spring.datasource.password={your password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


# JPA
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.hibernate.ddl-auto=none

# Redis
spring.data.redis.host={localhost}
spring.data.redis.port=6379 {default redis port}

#Logger
logging.level.org.springframework.security=DEBUG

# Swagger springdoc-ui Configuration
springdoc.packages-to-scan=com.spring.demo
springdoc.api-docs.path=/v3/api-docs
springdoc.api-docs.groups.enabled=true
springdoc.api-docs.enabled=true

springdoc.cache.disabled=true
springdoc.show-actuator=true

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operations-sorter=alpha
springdoc.swagger-ui.tags-sorter=alpha

springdoc.default-consumes-media-type=application/json;charset=UTF-8
springdoc.default-produces-media-type=application/json;charset=UTF-8

# JWT
jwt.secret.key={secret.key}

# mail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username={admin email}
spring.mail.password={pass-word}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.starttls.enable=true

# S3
cloud.aws.region.static=ap-northeast-2
cloud.aws.stack.auto-=false
cloud.aws.credentials.accessKey={ACCESS_KEY}
cloud.aws.credentials.secretKey={SECRET_KEY}
cloud.aws.s3.bucket=visiti-s3
```

## x.2. application-oauth.properties

```
#Naver
spring.security.oauth2.client.registration.naver.client-id=
spring.security.oauth2.client.registration.naver.client-secret=
spring.security.oauth2.client.registration.naver.client-name=Naver
spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.naver.redirect-uri=http://{your domain}/oauth/naver

#Naver Provider
spring.security.oauth2.client.provider.naver.authorization-uri=https://nid.naver.com/oauth2.0/authorize
spring.security.oauth2.client.provider.naver.token-uri=https://nid.naver.com/oauth2.0/token
spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me
spring.security.oauth2.client.provider.naver.user-name-attribute=response

#Kakao
spring.security.oauth2.client.registration.kakao.client-id=
spring.security.oauth2.client.registration.kakao.redirect-uri=http://{your domain}/oauth/kakao
spring.security.oauth2.client.registration.kakao.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.kakao.scope=account_email,profile_nickname
spring.security.oauth2.client.registration.kakao.client-name=Kakao
spring.security.oauth2.client.registration.kakao.client-authentication-method=POST

#Kakao Provider
spring.security.oauth2.client.provider.kakao.authorization-uri= https://kauth.kakao.com/oauth/authorize
spring.security.oauth2.client.provider.kakao.token-uri=https://kauth.kakao.com/oauth/token
spring.security.oauth2.client.provider.kakao.user-info-uri=https://kapi.kakao.com/v2/user/me
spring.security.oauth2.client.provider.kakao.user-name-attribute=id
```