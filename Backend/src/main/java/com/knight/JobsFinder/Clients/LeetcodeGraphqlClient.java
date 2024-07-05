package com.knight.JobsFinder.Clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knight.JobsFinder.Models.InterviewExperienceResponse;
import com.knight.JobsFinder.Models.Job;
import com.knight.JobsFinder.Utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.io.*;

import org.apache.http.impl.client.HttpClientBuilder;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;

@Service
@Slf4j
public class LeetcodeGraphqlClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${graphql.endpoint}")
    private String graphqlEndpoint;


    public List<InterviewExperienceResponse> executeGraphQLQuery(String query, Map<String, Object> variables) throws IOException {

        List<InterviewExperienceResponse> responses = new ArrayList<>();

        String requestBody = buildRequestBody(query, variables);
        HttpURLConnection connection = getHttpURLConnection(requestBody);

        int statusCode = connection.getResponseCode();
        InputStream inputStream = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream();
        String responseText = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));

        // Log the response for debugging
        System.out.println("Response Code: " + statusCode);
        System.out.println("Response: " + responseText);

        if (statusCode == 200 && isValidJson(responseText)) {
            JsonNode rootNode = objectMapper.readTree(responseText);
            JsonNode dataNode = rootNode.path("data").path("categoryTopicList").path("edges");

            if (dataNode.isArray()) {
                for (JsonNode node : dataNode) {
                    String title = node.path("node").path("title").asText();
                    String creationDate = node.path("node").path("post").path("creationDate").asText();

                        String company = Utils.extractCompanyName(title.toLowerCase());
                        if (!company.isBlank()) {
                            responses.add(new InterviewExperienceResponse(company, creationDate));

                    }
                }
            }
        } else {
            System.err.println("Unexpected response format or status code: " + statusCode);
        }

        return responses;
    }

    private HttpURLConnection getHttpURLConnection(String requestBody) throws IOException {
        byte[] body = requestBody.getBytes(StandardCharsets.UTF_8);
        URL url = new URL(graphqlEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        //   connection.setRequestProperty("Cookie", cookie);
        connection.setRequestProperty("Content-Length", Integer.toString(body.length));
        connection.setDoOutput(true);

        try (DataOutputStream output = new DataOutputStream(connection.getOutputStream())) {
            output.write(body);
        }
        return connection;
    }

    private static String buildRequestBody(String query, Map<String, Object> variables) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("query", query);
        body.put("variables", variables);
        return objectMapper.writeValueAsString(body);
    }

    // Utility method to convert a Map to JSON string
    private String mapToJson(Map<String, Object> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Error converting map to JSON", e);
        }
    }

    private static boolean isValidJson(String jsonResponse) {
        try {
            objectMapper.readTree(jsonResponse);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private List<InterviewExperienceResponse> mapResponse(HttpResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getEntity().getContent());

        List<InterviewExperienceResponse> res = new ArrayList<>();
        JsonNode edges = root.path("data").path("categoryTopicList").path("edges");

        for (JsonNode edge : edges) {
            JsonNode node = edge.path("node");
            String companyName = node.path("title").asText();
            String creationDate = node.path("post").path("creationDate").asText();
            res.add(new InterviewExperienceResponse(companyName, creationDate));
        }

        return res;
    }


}
