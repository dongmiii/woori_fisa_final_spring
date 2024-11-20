package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "SELECT DISTINCT title, url, summary, press_source FROM news_articles ORDER BY id DESC LIMIT 3", nativeQuery = true)
    List<News> findLatestThree();
}