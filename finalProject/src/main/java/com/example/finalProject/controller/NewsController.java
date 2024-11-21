package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.NewsEntity;
import com.example.finalProject.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // 기존 news 요청
    @GetMapping("/news")
    public String showNews(Model model) {
        List<NewsEntity> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        return "news";
    }
}