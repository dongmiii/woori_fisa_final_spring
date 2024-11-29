package com.example.finalProject.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.finalProject.domain.entity.MemberEntity;


@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{

	// email의 존재 여부 확인
	boolean existsByEmail(String email);

	Optional<MemberEntity> findByEmail(String email);

	// username으로 사용자 조회 (추가)
	Optional<MemberEntity> findByUsername(String username);

	@Query("SELECT m.id FROM MemberEntity m WHERE m.email = :email")
	Long findIdByEmail(@Param("email") String email);

	@Query("SELECT m.username FROM MemberEntity m WHERE m.email = :email")
	String findUsernameByEmail(@Param("email") String email);

	@Query("SELECT m.prompt FROM MemberEntity m WHERE m.email = :email")
	String findPromptByEmail(@Param("email") String email);





}