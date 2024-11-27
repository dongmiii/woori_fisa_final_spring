package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.CardEntity;
import com.example.finalProject.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/store")
    public String showStorePage(Model model) {
        List<CardEntity> cards = cardService.getTopThreeCards();
        model.addAttribute("cards", cards);
        return "store"; // store.html과 연결
    }
}