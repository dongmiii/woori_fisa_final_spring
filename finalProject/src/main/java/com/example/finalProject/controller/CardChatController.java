package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.ConversationEntity;
import com.example.finalProject.domain.entity.MessageEntity;
import com.example.finalProject.domain.entity.MemberEntity;
import com.example.finalProject.domain.repository.ConversationRepository;
import com.example.finalProject.domain.repository.MessageRepository;
import com.example.finalProject.dto.ChatRequestDTO;
import com.example.finalProject.service.ChatService;
import com.example.finalProject.service.MemberService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cardchat")
public class CardChatController {

//    private final ChatService chatService;
//
//    @Autowired
//    public ChatController(ChatService chatService) {
//        this.chatService = chatService;
//    }


    @Value("${fastapi.url}")
    private String fastApiurl;

    private String fastApiUrl; // final을 제거

    @PostConstruct
    public void init() {
        fastApiUrl = fastApiurl + "/cardchat";
    }// FastAPI 서버 URL
    private final MemberService memberService;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public CardChatController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> getChatResponse(@RequestBody ChatRequestDTO chatRequest, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Make sure question is not null or empty
            if (chatRequest.getQuestion() == null || chatRequest.getQuestion().trim().isEmpty()) {
                return ResponseEntity.status(400).body(Map.of("error", "Question cannot be empty"));
            }

            // Sending the request to FastAPI with 'question' field in the body
            ResponseEntity<String> response = restTemplate.postForEntity(fastApiUrl, chatRequest, String.class);

            // Parse response and handle result
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String result = jsonNode.get("response").asText(); // Get response text from FastAPI

            // Get cards and img_urls
            JsonNode cardsNode = jsonNode.get("cards");
            JsonNode imgUrlsNode = jsonNode.get("img_urls");

            // Prepare response map with all necessary fields
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("response", result);
            responseMap.put("cards", objectMapper.convertValue(cardsNode, List.class));
            responseMap.put("img_urls", objectMapper.convertValue(imgUrlsNode, List.class));

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error: " + e.getMessage()));
        }
    }


}

//    private ConversationEntity saveConversation(String title) {
//        ConversationEntity conversation = new ConversationEntity();
//        conversation.setTitle(title);
//        return conversationRepository.save(conversation);
//    }
//
//    private MessageEntity saveMessage(ConversationEntity conversation, MemberEntity user, String messageContent) {
//        MessageEntity message = new MessageEntity();
//        message.setConversation(conversation);
//        message.setUser(user); // MemberEntity 객체 사용
//        message.setMessage(messageContent);
//        return messageRepository.save(message);
//    }
//}