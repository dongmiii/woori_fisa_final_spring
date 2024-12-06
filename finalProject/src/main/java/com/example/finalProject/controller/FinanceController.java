package com.example.finalProject.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.finalProject.domain.entity.FinanceEntity;
import com.example.finalProject.service.FinanceService;

@Controller
//@RequestMapping("/finance")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/finance")
    public String showNews(Model model) {
    	Pageable pageable = PageRequest.of(0, 9);
    	List<Object[]> newsList= financeService.getPagedFinanceNews(pageable);
    	
    	model.addAttribute("financeList", newsList);
        return "news/finance";
    }
    
    
    @GetMapping("/finance/api/news")
    @ResponseBody
    public List<Object[]> getpagedNews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size){
    	return financeService.getPagedFinanceNews(PageRequest.of(page, size));
    }
    
}