package com.example.finalProject.service;

import com.example.finalProject.domain.entity.ChatEntity;
import com.example.finalProject.domain.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    // 대화 저장
    public ChatEntity saveChat(Integer userId, String title, String userMessage, String botMessage) {
        ChatEntity chatEntity = new ChatEntity(userId, title, userMessage, botMessage, java.time.LocalDateTime.now());
        return chatRepository.save(chatEntity);  // 저장된 대화 반환
    }

    // 모든 대화 내역 조회
    public List<ChatEntity> getAllChats() {
        return chatRepository.findAll();  // 모든 대화 내역 반환
    }

    // 특정 대화 조회 (ID로 찾기)
    public Optional<ChatEntity> getChatById(Long id) {
        return chatRepository.findById(id);  // 대화 ID로 검색
    }
}