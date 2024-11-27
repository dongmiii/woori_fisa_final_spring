package com.example.finalProject.domain.repository;


import com.example.finalProject.domain.entity.CardEntity;
import com.example.finalProject.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardApiController {

    private final CardService cardService;

    public CardApiController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/api/cards")
    public List<CardEntity> getCards() {
        // 상위 3개의 카드만 반환
        return cardService.getTopThreeCards();
    }
}