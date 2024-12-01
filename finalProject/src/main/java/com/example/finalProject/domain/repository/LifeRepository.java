package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.LifeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeRepository extends JpaRepository<LifeEntity, Long> {

    @Query(value = "SELECT DISTINCT title, url, summary, press_source FROM life_news_articles ORDER BY id DESC LIMIT 3", nativeQuery = true)
    List<LifeEntity> findLatestThree();
}