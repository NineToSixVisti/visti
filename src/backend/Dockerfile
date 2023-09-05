FROM gradle:jdk17 as builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:17.0-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /build/build/libs/demo-0.0.1-SNAPSHOT.jar .

EXPOSE 8080
# root 대신 nobody 권한으로 실행
USER nobody
CMD [ \
    "java", \
    "-jar", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dsun.net.inetaddr.ttl=0", \
    "demo-0.0.1-SNAPSHOT.jar"\
]