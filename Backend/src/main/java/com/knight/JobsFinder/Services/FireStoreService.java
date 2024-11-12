package com.knight.JobsFinder.Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
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
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FireStoreService {

    private final Firestore firestore;

    public FireStoreService() throws IOException {
        // Load the service account file from resources
        ClassPathResource resource = new ClassPathResource("service-account-file.json");
        try (InputStream serviceAccountStream = resource.getInputStream()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);
            FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                    .setCredentials(credentials)
                    .setProjectId("techarticles-407117")
                    .build();
            this.firestore = firestoreOptions.getService();
        }
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

