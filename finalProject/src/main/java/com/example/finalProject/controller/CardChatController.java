package com.example.finalProject.controller;

import com.example.finalProject.dto.ChatRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/cardchat")
public class CardChatController {

    private final String fastApiUrl = "http://localhost:5555/cardchat";

    @PostMapping
    public String getChatResponse(@RequestBody ChatRequestDTO chatRequest) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(fastApiUrl, chatRequest, String.class);
        return response.getBody();
    }
}