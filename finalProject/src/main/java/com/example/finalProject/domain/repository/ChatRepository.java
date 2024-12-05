<<<<<<< HEAD
package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    // 필요한 추가 쿼리 메소드가 있으면 여기에 정의
=======
package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    // 필요한 추가 쿼리 메소드가 있으면 여기에 정의
>>>>>>> d32320b3ead6a9af7f887248e22240d0f9de3610
}