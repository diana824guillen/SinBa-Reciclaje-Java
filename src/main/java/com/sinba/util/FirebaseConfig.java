package com.sinba.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;

import java.io.InputStream;

public class FirebaseConfig {
    private static Firestore firestore;

    public static Firestore getFirestore() {
        if (firestore == null) {
            try {
                InputStream serviceAccount = FirebaseConfig.class
                        .getClassLoader()
                        .getResourceAsStream("serviceAccountKey.json");
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setProjectId("sinba-reciclaje")
                        .build();
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }
                firestore = FirestoreClient.getFirestore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return firestore;
    }
}