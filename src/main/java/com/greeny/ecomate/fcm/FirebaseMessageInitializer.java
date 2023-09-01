package com.greeny.ecomate.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class FirebaseMessageInitializer {

    @Value("${firebase.service-key}")
    private String FIREBASE_CONFIG_PATH;

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount =
                    new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
