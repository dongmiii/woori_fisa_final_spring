package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.CardEntity;
import com.example.finalProject.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CardApiController {

    private final CardService cardService;

    public CardApiController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/api/cards")
    public ResponseEntity<?> getCards(
            @RequestParam(required = true) Long userId, // 사용자 ID 필수
            @RequestParam(required = true) String type, // 유형 필수 (e.g., "convenience", "cafe")
            @RequestParam(required = false, defaultValue = "") String keyword // 검색어 선택 (e.g., "GS25")
    ) {

        List<CardEntity> allCards;
        List<CardEntity> filteredCards;

        // type에 따라 동적으로 로직 처리
        switch (type) {
            case "convenience":
                allCards = cardService.getCardsByUserIdAndConvenience(userId, "");
                if (allCards.isEmpty()) {
                    return ResponseEntity.ok(Map.of("message", "결제 및 카드 정보가 등록되어있지 않습니다."));
                }
                filteredCards = cardService.getCardsByUserIdAndConvenience(userId, keyword);
                break;

            case "cafe":
                allCards = cardService.getCardsByUserIdAndCafe(userId, "");
                if (allCards.isEmpty()) {
                    return ResponseEntity.ok(Map.of("message", "결제 및 카드 정보가 등록되어있지 않습니다."));
                }
                filteredCards = cardService.getCardsByUserIdAndCafe(userId, keyword);
                break;

            // 다른 유형 추가 가능
            case "oil":
                allCards = cardService.getCardsByUserIdAndOil(userId, "");
                if (allCards.isEmpty()) {
                    return ResponseEntity.ok(Map.of("message", "결제 및 카드 정보가 등록되어있지 않습니다."));
                }
                filteredCards = cardService.getCardsByUserIdAndOil(userId, keyword);
                break;


            case "mart":
                allCards = cardService.getCardsByUserIdAndMart(userId, "");
                if (allCards.isEmpty()) {
                    return ResponseEntity.ok(Map.of("message", "결제 및 카드 정보가 등록되어있지 않습니다."));
                }
                filteredCards = cardService.getCardsByUserIdAndMart(userId, keyword);
                break;


            case "movie":
                allCards = cardService.getCardsByUserIdAndMovie(userId, "");
                if (allCards.isEmpty()) {
                    return ResponseEntity.ok(Map.of("message", "결제 및 카드 정보가 등록되어있지 않습니다."));
                }
                filteredCards = cardService.getCardsByUserIdAndMovie(userId, keyword);
                break;

            default:
                return ResponseEntity.badRequest().body(Map.of("message", "잘못된 요청 유형입니다."));

        }

        return ResponseEntity.ok(filteredCards);
    }
}
