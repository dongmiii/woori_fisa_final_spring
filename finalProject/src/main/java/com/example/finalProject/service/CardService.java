package com.example.finalProject.service;

import com.example.finalProject.domain.entity.CardEntity;
import com.example.finalProject.domain.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<CardEntity> getTopThreeCards() {
        List<CardEntity> cards = cardRepository.findAll().stream().limit(3).toList();
        logger.info("Fetched Cards: {}", cards); // 가져온 데이터 로그 출력
        return cards;
    }
}