package com.example.finalProject.controller;

import com.example.finalProject.service.DashboardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 직전 달 소비 카테고리별 퍼센트를 세션의 userId로 조회 후 전달
     * @param session HTTP 세션 객체
     * @param model Spring Model 객체
     * @return 대시보드 HTML 뷰 이름
     */
    @GetMapping
    public String getDashboardData(HttpSession session, Model model) {
        // 세션에서 userId 가져오기
        Integer userId = (Integer) session.getAttribute("userid");

        if (userId == null) {
            // 세션에 userId가 없는 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        try {
            Map<String, Double> categoryPercentages = dashboardService.getLastMonthCategoryPercentages(userId);
            System.out.println("categoryPercentages: " + categoryPercentages);
            
            Map<String, Double> cardSpendingData = dashboardService.getCardSpendingData(userId);
            System.out.println("Card Spending Data: " + cardSpendingData);
            
            String categoryPercentagesJson = objectMapper.writeValueAsString(categoryPercentages);
            String cardSpendingDataJson = objectMapper.writeValueAsString(cardSpendingData);

            // 데이터를 JSON 문자열로 변환하여 모델에 추가
            model.addAttribute("categoryPercentages", categoryPercentagesJson);
            model.addAttribute("cardSpendingData", cardSpendingDataJson);
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Graph 3: 최근 7일 소비 추이
//        Map<String, Double> spendingTendencyData = dashboardService.getLast7DaysSpendingTendency(userId);
//        model.addAttribute("spendingTendencyData", spendingTendencyData);
//
//        // Graph 4: 가장 많이 소비한 날과 일일 평균 비교
//        Map<String, Double> dayComparisonData = dashboardService.getDayComparisonData(userId);
//        model.addAttribute("dayComparisonData", dayComparisonData);

        return "dashboard/dashboard";
    }
}
