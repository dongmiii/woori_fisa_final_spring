package com.example.finalProject.controller;

import com.example.finalProject.dto.ChatRequestDTO;
import com.example.finalProject.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public String getChatResponse(@RequestBody ChatRequestDTO chatRequest) {
        return chatService.getChatResponse(chatRequest.getPrompt());
    }

}