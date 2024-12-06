package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.CardEntity;
import com.example.finalProject.service.CardService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/store")
    public String showStorePage(
            @RequestParam(required = false) String convenience, // 편의점 조건
            HttpSession session,
            Model model) {
        return getCardsForPage("store", convenience, session, model, "convenience");
    }

    @GetMapping("/cafe")
    public String showCafePage(
            @RequestParam(required = false) String cafe, // 카페 조건
            HttpSession session,
            Model model) {
        return getCardsForPage("cafe", cafe, session, model, "cafe");
    }

    @GetMapping("/oil")
    public String showOilPage(
            @RequestParam(required = false) String oil, // 주유소 조건
            HttpSession session,
            Model model) {
        return getCardsForPage("oil", oil, session, model, "oil");
    }

    @GetMapping("/mart")
    public String showMartPage(
            @RequestParam(required = false) String mart, // 마트 조건
            HttpSession session,
            Model model) {
        return getCardsForPage("mart", mart, session, model, "mart");
    }

    @GetMapping("/movie")
    public String showMoviePage(
            @RequestParam(required = false) String movie, // 영화 조건
            HttpSession session,
            Model model) {
        return getCardsForPage("movie", movie, session, model, "movie");
    }

    /**
     * 공통 로직을 처리하기 위한 메서드
     *
     * @param pageName 페이지 이름 (store, cafe 등)
     * @param keyword 검색 키워드 (GS25, 스타벅스 등)
     * @param session 세션
     * @param model 모델 객체
     * @param type 유형 (convenience, cafe 등)
     * @return HTML 페이지 이름
     */
    private String getCardsForPage(String pageName, String keyword, HttpSession session, Model model, String type) {
        // 세션에서 사용자 ID 가져오기
        Long userId = (Long) session.getAttribute("userid");

        if (userId != null) {
            // 카드 리스트 가져오기
            List<CardEntity> cards = getCardsByType(userId, keyword, type);
            if (cards.isEmpty()) {
                model.addAttribute("errorMessage", "결제 및 카드 정보가 등록되어있지 않습니다.");
            } else {
                model.addAttribute("cards", cards);
            }
        }

        // 검색 키워드가 없으면 전체를 의미하는 "전체" 추가
        model.addAttribute(type, keyword == null ? "전체" : keyword);
        return pageName; // 페이지 이름 반환
    }

    /**
     * 카드 데이터를 유형별로 가져오기
     *
     * @param userId 사용자 ID
     * @param keyword 검색 키워드
     * @param type 유형
     * @return 카드 리스트
     */
    private List<CardEntity> getCardsByType(Long userId, String keyword, String type) {
        switch (type) {
            case "convenience":
                return cardService.getCardsByUserIdAndConvenience(userId, keyword == null ? "" : keyword);
            case "cafe":
                return cardService.getCardsByUserIdAndCafe(userId, keyword == null ? "" : keyword);
            case "oil":
                return cardService.getCardsByUserIdAndOil(userId, keyword == null ? "" : keyword);
            case "mart":
                return cardService.getCardsByUserIdAndMart(userId, keyword == null ? "" : keyword);
            case "movie":
                return cardService.getCardsByUserIdAndMovie(userId, keyword == null ? "" : keyword);
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
}