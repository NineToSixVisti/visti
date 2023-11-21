
<img src="https://github.com/NineToSixVisti/visti/assets/122581063/ef81079d-af4e-4e49-a3c8-766a254b6c51"/>

<br>
<br>
<br>

# 서비스 소개

추억을 NFT화시키고 친한사람들끼리 추억을 공유할 수 있는 sns 서비스

[비스티 바로가기](https://visti-story.com/storybox)<앱 배포중>

<br>
<br>
<br>

# 프로젝트 소개

23.08.14 ~ 23.10.06 (6주)

총 6명 (프론트 2 / 백 2 / 모바일 2)

<br>
<br>
<br>

# 개발 환경

**FE** | `React` `Redux`  `TypeScript`  `PWA`

**BE** | `Redis` `MySQL` `S3`

**Mobile** | `Android` `Jetpack Compose`

**BE - API** | `Java` `SpringBoot` `JPA` `SpringSecurity` `Swagger` `Postman`

**BE - Mobile** | `FireBaseCloud`

**CI/CD** | `AWS` `EC2` `Docker` `Jenkins` `Nginx`

<br>
<br>
<br>

# 주요 화면

|                                                                                                           |                                                                                                           |
| --------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/NineToSixVisti/visti/assets/122581063/00bb1787-b5fb-438b-8cbe-0fcbf779692e"> | <img src="https://github.com/NineToSixVisti/visti/assets/122581063/00255102-5e89-4960-bb24-522692a3fcae"> |
| `메인 화면`                                                                                               | `스토리 박스생성`                                                                                         |
| <img src="https://github.com/NineToSixVisti/visti/assets/122581063/c7702306-c6cc-405c-9134-37af8d4d1a80"> | <img src="https://github.com/NineToSixVisti/visti/assets/122581063/413512f6-8d87-44e7-b7e2-09dd28fc4566"> |
| `스토리 생성`                                                                              | `좋아요 페이지`                                                           |

<br>
<br>
<br>

# 아키텍처

<img width="70%" src="./docs/visti_architecture.png">

<br>
<br>
<br>

# ERD

<img width="70%" src="./docs/visti_ERD.png">

<br>
<br>
<br>

# 와이어 프레임
[<img width="70%" src="./docs/전체 와이어 프레임.png">](https://github.com/NineToSixVisti/visti/tree/master/src/frontend/visti)



<br>
<br>
<br>

</div>

</div>

## 1. application.properties

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

## 2. application-oauth.properties

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

926
