package com.spring.visti.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException{
        ClassPathResource resource = new ClassPathResource("visti-e32d0-firebase-adminsdk-nefcc-1a705a8042.json");

        InputStream refreshToken = resource.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
