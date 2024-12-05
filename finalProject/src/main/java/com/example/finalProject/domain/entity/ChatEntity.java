package com.example.finalProject.domain.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer userId;  // user_id를 INT로 변경

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String userMessage;

    @Column(nullable = false)
    private String botMessage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 기본 생성자
    public ChatEntity() {}

    // 생성자
    public ChatEntity(Integer userId, String title, String userMessage, String botMessage, LocalDateTime createdAt) {
        this.userId = userId;
        this.title = title;
        this.userMessage = userMessage;
        this.botMessage = botMessage;
        this.createdAt = createdAt;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(String botMessage) {
        this.botMessage = botMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}