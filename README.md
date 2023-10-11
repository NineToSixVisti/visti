# ignore 된 피알 목록

<div align=center>

<br>
<br>

<img src="https://user-images.githubusercontent.com/59721896/231941106-837444e7-bc93-45f1-ae1a-38497ea4f45e.png"/>

<br>
<br>
<br>

# 서비스 소개

술내음은 사용자에게 딱 맞는 전통주를 추천해주고, 우리술을 홍보하는 서비스입니다.

술내음과 함께 당신에게 딱 맞는 전통주를 찾아보세요.

[술내음 바로가기](https://j8a707.p.ssafy.io/)

<br>
<br>
<br>

# 프로젝트 소개

🏆 SSAFY 8기 2학기 특화 **우수 프로젝트** 🏆

23.02.27 ~ 23.04.07 (6주)

총 6명 (프론트 3 / 백 3)

<br>
<br>
<br>

# 개발 환경

**FE** | `React` `Redux` `TailWind CSS` `TypeScript`

**BE** | `ElasticSearch` `RabbitMQ` `MySQL` `S3`
 
**BE - API** | `Java` `SpringBoot` `JPA` `SpringSecurity` `Swagger` `Postman`

**BE - Analysis** | `Python` `Flask`

**CI/CD** | `AWS` `EC2` `RDS` `Docker` `Jenkins` `Nginx` `Docker Compose`

<br>
<br>
<br>

# 주요 화면

|||
|---|---|
|<img src="https://user-images.githubusercontent.com/59721896/231935159-46048a19-0043-452e-888d-6c55705fab6b.gif">|<img src="https://user-images.githubusercontent.com/59721896/231935775-97f354b5-db19-4e7c-9a2d-abdd117f7f57.gif">|
|`메인 화면` 서비스 소개|`전통주 검색` ElasticSearch|
|<img src="https://user-images.githubusercontent.com/59721896/231935357-de6b71b9-3abd-4c48-a9ef-a331122d917e.gif">|<img src="https://user-images.githubusercontent.com/59721896/231945641-3333531f-9df2-4673-90c6-1a55564385da.gif">|
|`전통주 페이지` 카테고리, 정렬|`전통주 상세 페이지` 안주, 비슷한 술, 맛 보정, 리뷰, 식당|
|<img src="https://user-images.githubusercontent.com/59721896/231936088-17f12ae7-d212-415a-8757-428b86fb4e6a.gif">|<img src="https://user-images.githubusercontent.com/59721896/231944103-8e13e8b7-17d0-4a2a-b4fe-c91b7eb4cf8f.gif">|
|`전통주 지도` 양조장, 축제, 체험 프로그램|`나만의 전통주 추천` |
|<img src="https://user-images.githubusercontent.com/59721896/231945138-9474580a-7229-4828-9a50-86ac5cefc0f6.gif">|<img src="https://user-images.githubusercontent.com/59721896/231937512-ffdbcfc1-e79a-4934-95e6-b553bc4ae5d8.gif">|
|`맞춤 선물 추천` 酒bti 검사 결과 기반 선물 추천|`랜덤 추천` 건배사, 술, 안주|
|<img src="https://user-images.githubusercontent.com/59721896/231947919-54fccaf0-7753-4a95-842c-ae28c9300db0.gif">|<img src="https://user-images.githubusercontent.com/59721896/231947930-a1ca2e7b-dca1-47ab-b033-542abd4690c1.gif">|
|`전통주 유형 검사(酒bti)` 서비스 유입, 유저 데이터 수집|`마이페이지` 텍스트 마이닝, 내가 클리어했거나 찜한 전통주 및 식당 |

<br>
<br>
<br>

# 아키텍처

<img width="70%" src="https://user-images.githubusercontent.com/59721896/231405016-916dd3b6-6a2d-4218-8c7f-a9220382c459.png">

<br>
<br>
<br>

# ERD

<img width="70%" src="https://user-images.githubusercontent.com/59721896/231406400-f64002b7-f93c-40c3-9867-fad448ecc124.png">

<br>
<br>
<br>

# 디자인 가이드

<img width="70%" src="https://user-images.githubusercontent.com/59721896/231400674-ca21c4b2-a593-4b05-af70-c87900272932.png">

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
