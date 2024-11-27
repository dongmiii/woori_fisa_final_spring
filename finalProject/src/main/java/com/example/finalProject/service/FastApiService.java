package com.example.finalProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class FastApiService {

    private final RestTemplate restTemplate; // RestTemplate 주입

    public String sendname(String name) {
        String FastApiUrl = "http://127.0.0.1:8000/receive-name"; // FastAPI 엔드포인트

        // 요청 데이터 준비
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String requestBody = "{\"name\":\"" + name + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // FastAPI에 POST 요청
        try {
            // POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(FastApiUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                System.err.println("FastAPI returned error: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send name to FastAPI";
        }
    }
}