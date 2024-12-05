package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
    // 커스텀 쿼리 메서드가 필요한 경우 여기에 추가할 수 있습니다
}