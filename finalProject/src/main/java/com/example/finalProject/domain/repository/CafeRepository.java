package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CafeRepository extends JpaRepository<CardEntity, Long> {

    @Query("SELECT c FROM CardEntity c WHERE c.name IN " +
            "(SELECT DISTINCT p.cardName FROM PaymentEntity p WHERE p.memberId = :userId) " +
            "AND c.cafe LIKE %:cafe%")
    List<CardEntity> findCardsByUserIdAndCafe(@Param("userId") Long userId, @Param("cafe") String cafe);
}