package com.example.finalProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class FastApiService {

    private final RestTemplate restTemplate; // RestTemplate 주입

    public String sendname(String name) {
        String apiUrl = "http://127.0.0.1:8000/receive-name"; // FastAPI 엔드포인트

        // 요청 데이터 설정
        Map<String, String> request = Map.of("name", name);

        // FastAPI에 POST 요청
        try {
            String response = restTemplate.postForObject(apiUrl, request, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send name to FastAPI";
        }
    }
}