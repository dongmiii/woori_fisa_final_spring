package com.example.finalProject.domain.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "conversations")
public class ConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // id를 BIGINT로 설정할 경우 Long 타입 사용

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    // 연관관계 설정: 한 대화에는 여러 개의 메시지가 있을 수 있습니다.
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MessageEntity> messages;  // 'Message' -> 'MessageEntity'로 변경

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }
}