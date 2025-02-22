package com.knight.JobsFinder.Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FireStoreService {

    private final Firestore firestore;

    public FireStoreService() throws IOException {
        GoogleCredentials credentials;

//         Check if GOOGLE_APPLICATION_CREDENTIALS_BASE64 is set (Railway)
        String base64Creds = System.getenv("GOOGLE_APPLICATION_CREDENTIALS_BASE64");

        if (base64Creds != null && !base64Creds.isEmpty()) {
            // Decode Base64 JSON
            byte[] decodedBytes = Base64.getDecoder().decode(base64Creds);

            // Write to a temporary file
            File tempFile = File.createTempFile("service-account", ".json");
            tempFile.deleteOnExit(); // Auto-delete when the app stops

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(decodedBytes);
            }

            // Load credentials from the temporary file
            try (FileInputStream serviceAccountStream = new FileInputStream(tempFile)) {
                credentials = GoogleCredentials.fromStream(serviceAccountStream);
            }
        } else {
            // Fallback: Load from resources (Local Development)
            ClassPathResource resource = new ClassPathResource("service-account-file.json");
            try (InputStream serviceAccountStream = resource.getInputStream()) {
                credentials = GoogleCredentials.fromStream(serviceAccountStream);
            }
        }

        // Initialize Firestore
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId("techarticles-407117")
                .build();
        this.firestore = firestoreOptions.getService();
    }


    public List<QueryDocumentSnapshot> getCompanyDocumentsAfterTimestamp(String companyName, long timestamp)
            throws ExecutionException, InterruptedException {

        // Create a query for documents with the specified company name and timestamp greater than the given value
        Query query = firestore.collection("questionbank")
                .whereEqualTo("companyName", companyName)
                .whereGreaterThan("time_stamp", timestamp);

        // Execute the query
        QuerySnapshot querySnapshot = query.get().get();

        // Return the list of documents that match the criteria
        return querySnapshot.getDocuments();

    }
}


