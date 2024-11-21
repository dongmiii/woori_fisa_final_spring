package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.News2Entity;
import com.example.finalProject.service.News2Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class News2Controller {

    private final News2Service news2Service;

    public News2Controller(News2Service newsService) {
        this.news2Service = newsService;
    }

    // 기존 news 요청
    @GetMapping("/news2")
    public String showNews(Model model) {
        List<News2Entity> newsList = news2Service.getAllNews();
        model.addAttribute("newsList", newsList);
        return "news";
    }
}