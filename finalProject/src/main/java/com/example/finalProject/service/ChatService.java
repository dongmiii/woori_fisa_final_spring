package com.example.finalProject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String getChatResponse(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant."),
                Map.of("role", "user", "content", prompt)
        ));
        requestBody.put("max_tokens", 100);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                Map<String, Object> firstChoice = ((List<Map<String, Object>>) responseBody.get("choices")).get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                return message.get("content").toString().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "OpenAI API 호출 중 오류가 발생했습니다.";
        }

        return "응답이 없습니다.";
    }
}