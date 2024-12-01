package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    // 예시: 특정 대화에 대한 메시지를 찾는 쿼리 메서드
    List<MessageEntity> findByConversationId(Long conversationId);

    // 예시: 특정 유저의 메시지를 찾는 쿼리 메서드
    List<MessageEntity> findByUserId(Long userId);
}