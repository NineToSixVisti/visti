package com.spring.visti.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * S3 Config 입니다.
 * */

@Configuration
public class S3Config {
    // yml, properties 설정 값을 담아주기
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    // S3 객체와 연결
    @Bean
    public AmazonS3 amazonS3Client() {
        // 인증 정보 생성
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // Client 생성
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

//    @Bean
//    public AmazonS3Client amazonS3Client() {
//        // AmazonS3Client를 생성하고 설정
//        return new AmazonS3Client();
//    }
}