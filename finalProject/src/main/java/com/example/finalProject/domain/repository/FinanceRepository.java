package com.example.finalProject.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.finalProject.domain.entity.FinanceEntity;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceEntity, Long> {

//    @Query(value = "SELECT DISTINCT title, url, summary, press_source FROM finance_news_articles ORDER BY id DESC LIMIT 3", nativeQuery = true)
//    List<FinanceEntity> findLatestThree();
	
    @Query(value = "SELECT DISTINCT f.title, f.summary FROM FinanceEntity f")
    List<Object[]> findFinanceDistinct();

    @Query("SELECT DISTINCT f.title, f.summary FROM FinanceEntity f")
	Page<Object[]> findPagedFinanceDistinct(Pageable pageable);
	
//	@Query(value = "SELECT DISTINCT f.title, f.summary FROM FinanceEntity f")
//	Page<Object[]> findAllDistinct(Pageable pageable);
}