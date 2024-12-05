package com.example.finalProject.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.finalProject.service.LifeService;

@Controller
public class LifeController {

    private final LifeService lifeService;

    public LifeController(LifeService lifeService) {
        this.lifeService = lifeService;
    }

    
    @GetMapping("/life")
    public String showNews(Model model) {
    	Pageable pageable = PageRequest.of(0, 9);
    	List<Object[]> newsList= lifeService.getPagedFinanceNews(pageable);
    	
    	model.addAttribute("lifeList", newsList);
        return "news/life";
    }
    
    
    @GetMapping("/life/api/news")
    @ResponseBody
    public List<Object[]> getpagedNews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size){
    	return lifeService.getPagedFinanceNews(PageRequest.of(page, size));
    }
}