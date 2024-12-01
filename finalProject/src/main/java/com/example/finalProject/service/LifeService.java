package com.example.finalProject.service;

import com.example.finalProject.domain.entity.LifeEntity;
import com.example.finalProject.domain.repository.LifeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeService {
    private final LifeRepository news2Repository;

    public LifeService(LifeRepository newsRepository) {
        this.news2Repository = newsRepository;
    }

    public List<LifeEntity> getAllNews() {
        return news2Repository.findAll();
    }
}