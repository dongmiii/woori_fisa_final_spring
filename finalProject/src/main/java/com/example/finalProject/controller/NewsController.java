package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.News;
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

    @GetMapping("/news")
    public String showNews(Model model) {
        // NewsService에서 모든 뉴스 데이터를 가져옵니다.
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList); // HTML에 전달
        return "news"; // news.html 렌더링
    }
}