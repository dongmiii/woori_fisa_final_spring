package com.example.finalProject.domain.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One 관계 설정: 하나의 대화에 여러 메시지가 있을 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;  // ConversationEntity 클래스 사용

    // Many-to-One 관계 설정: 하나의 메시지는 하나의 유저에 속함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MemberEntity user;  // MemberEntity 클래스를 참조

    @Column(nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    // Builder를 사용하여 생성
    @Builder
    public MessageEntity(Long id, ConversationEntity conversation, MemberEntity user, String message) {
        this.id = id;
        this.conversation = conversation;
        this.user = user;
        this.message = message;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }

    public MemberEntity getUser() {
        return user;
    }

    public void setUser(MemberEntity user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}