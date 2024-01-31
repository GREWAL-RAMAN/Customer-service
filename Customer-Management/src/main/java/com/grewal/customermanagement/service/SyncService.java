package com.grewal.customermanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grewal.customermanagement.Config.RemoteApiConfig;
import com.grewal.customermanagement.dto.CustomerResponse;
import com.grewal.customermanagement.model.AuthResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SyncService {

    private final RemoteApiConfig remoteApiConfig;

    private final RestTemplate restTemplate;

    @PostConstruct
    public void initialize() {
        // Add StringHttpMessageConverter with UTF-8 encoding
    }

    // Other methods...

    public String authenticateAndGetToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set your login credentials
        String loginId = "test@sunbasedata.com";
        String password = "Test@123";

        // Create JSON body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("login_id", remoteApiConfig.getUsername());
        requestBody.put("password", remoteApiConfig.getPassword());

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    remoteApiConfig.getAuthApiUrl(),
                    request,
                    String.class);

                String responseBody = responseEntity.getBody();
                // Assuming AuthResponse class has a method to retrieve the access token
                AuthResponse authResponse = parseJsonResponse(responseBody, AuthResponse.class);
                return "Bearer " + authResponse.getAccessToken();

    }

    private <T> T parseJsonResponse(String responseBody, Class<T> responseType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(responseBody, responseType);
        } catch (IOException e) {
            // Handle parsing exception
            e.printStackTrace();
            return null;
        }
    }

    public List<CustomerResponse> syncCustomers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a RequestCallback to add headers to the request
        RequestCallback requestCallback = request -> request.getHeaders().addAll(headers);

        ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                remoteApiConfig.getGetApiUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

// Check the HTTP status code
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            List<CustomerResponse> customerList = parseJsonResponse(responseBody);
            return customerList;
        } else {
            throw new RuntimeException("Failed to fetch customer list. Status code: " + responseEntity.getStatusCodeValue());
        }


    }

    private List<CustomerResponse> parseJsonResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(responseBody, new TypeReference<List<CustomerResponse>>() {});
        } catch (IOException e) {
            // Handle parsing exception
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
