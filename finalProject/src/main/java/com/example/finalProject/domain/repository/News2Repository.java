package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.News2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface News2Repository extends JpaRepository<News2Entity, Long> {

    @Query(value = "SELECT DISTINCT title, url, summary, press_source FROM life_news_articles ORDER BY id DESC LIMIT 3", nativeQuery = true)
    List<News2Entity> findLatestThree();
}