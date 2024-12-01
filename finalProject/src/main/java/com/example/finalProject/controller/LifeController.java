package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.LifeEntity;
import com.example.finalProject.service.LifeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LifeController {

    private final LifeService news2Service;

    public LifeController(LifeService newsService) {
        this.news2Service = newsService;
    }

    // 기존 news 요청
    @GetMapping("/life")
    public String showNews(Model model) {
        List<LifeEntity> newsList = news2Service.getAllNews();
        model.addAttribute("newsList", newsList);
        return "/news/life";
    }
}