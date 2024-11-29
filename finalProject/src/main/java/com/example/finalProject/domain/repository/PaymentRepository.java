package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    // 특정 날짜 범위의 결제 내역 조회
    @Query("SELECT p FROM PaymentEntity p WHERE p.datetime >= :start AND p.datetime < :end")
    List<PaymentEntity> findByDatetime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    

    @Query("SELECT SUM(p.amount) FROM PaymentEntity p WHERE p.datetime >= :start AND p.datetime < :end")
    Long calculateTotalAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


//	List<PaymentEntity> findByMemberId(Integer memberId);
    
    @Query("SELECT p FROM PaymentEntity p WHERE p.memberId = :memberId")
    List<PaymentEntity> findByMemberId(@Param("memberId") Integer memberId);


    @Query("SELECT p FROM PaymentEntity p WHERE p.memberId = :memberId AND p.datetime BETWEEN :start AND :end")
    List<PaymentEntity> findByMemberIdAndDatetimeBetween(
        @Param("memberId") Integer memberId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );


    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM PaymentEntity p WHERE p.memberId = :memberId AND p.datetime BETWEEN :start AND :end")
    Long calculateTotalAmountForUser(
        @Param("memberId") Integer memberId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
    
    
}