package com.example.finalProject.service;

import com.example.finalProject.domain.entity.News2Entity;
import com.example.finalProject.domain.repository.News2Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class News2Service {
    private final News2Repository news2Repository;

    public News2Service(News2Repository newsRepository) {
        this.news2Repository = newsRepository;
    }

    public List<News2Entity> getAllNews() {
        return news2Repository.findAll();
    }
}