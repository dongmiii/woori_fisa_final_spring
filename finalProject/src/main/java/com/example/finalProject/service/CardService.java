package com.example.finalProject.service;

import com.example.finalProject.domain.entity.CardEntity;
import com.example.finalProject.domain.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    private final StoreRepository storeRepository;
    private final CafeRepository cafeRepository;
    private final OilRepository oilRepository;
    private final MovieRepository movieRepository;
    private final MartRepository martRepository;

    public CardService(StoreRepository storeRepository, CafeRepository cafeRepository, OilRepository oilRepository, MovieRepository movieRepository, MartRepository martRepository) {
        this.storeRepository = storeRepository;
        this.cafeRepository = cafeRepository;
        this.oilRepository = oilRepository;
        this.martRepository = martRepository;
        this.movieRepository = movieRepository;
    }

    public List<CardEntity> getCardsByUserIdAndConvenience(Long userId, String keyword) {
        List<CardEntity> storeCards = storeRepository.findCardsByUserIdAndConvenience(userId, keyword).stream().toList();
        logger.info("Fetched Cards: {}", storeCards); // 가져온 데이터 로그 출력
        return storeCards;
    }

    public List<CardEntity> getCardsByUserIdAndCafe(Long userId, String keyword) {
        List<CardEntity> cafeCards = cafeRepository.findCardsByUserIdAndCafe(userId, keyword).stream().toList();
        logger.info("Fetched Cards: {}", cafeCards); // 가져온 데이터 로그 출력
        return cafeCards;
    }

    public List<CardEntity> getCardsByUserIdAndOil(Long userId, String keyword) {
        List<CardEntity> oilCards = oilRepository.findCardsByUserIdAndOil(userId, keyword).stream().toList();
        logger.info("Fetched Cards: {}", oilCards); // 가져온 데이터 로그 출력
        return oilCards;
    }

    public List<CardEntity> getCardsByUserIdAndMart(Long userId, String keyword) {
        List<CardEntity> martCards = martRepository.findCardsByUserIdAndMart(userId, keyword).stream().toList();
        logger.info("Fetched Cards: {}", martCards); // 가져온 데이터 로그 출력
        return martCards;
    }

    public List<CardEntity> getCardsByUserIdAndMovie(Long userId, String keyword) {
        List<CardEntity> movieCards = movieRepository.findCardsByUserIdAndMovie(userId, keyword).stream().toList();
        logger.info("Fetched Cards: {}", movieCards); // 가져온 데이터 로그 출력
        return movieCards;
    }
}