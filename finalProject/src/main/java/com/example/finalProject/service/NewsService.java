package com.example.finalProject.service;

import com.example.finalProject.domain.entity.NewsEntity;
import com.example.finalProject.domain.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }
}