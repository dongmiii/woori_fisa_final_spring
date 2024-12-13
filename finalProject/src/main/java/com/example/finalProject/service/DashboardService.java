package com.example.finalProject.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalProject.domain.entity.PaymentEntity;
import com.example.finalProject.domain.repository.PaymentRepository;

@Service
public class DashboardService {
    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * 직전 달의 소비 카테고리별 퍼센트를 계산합니다.
     * @param memberId 사용자 ID
     * @return 카테고리별 소비 퍼센트 (카테고리명, 퍼센트)
     */
    public Map<String, Double> getLastMonthCategoryPercentages(Integer memberId) {
        LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        List<PaymentEntity> payments = paymentRepository.findByMemberIdAndDatetimeBetween(memberId, start, end);

        double totalAmount = payments.stream()
                .mapToDouble(p -> p.getAmount().doubleValue())
                .sum();

        if (totalAmount == 0) {
            return Collections.emptyMap();
        }

        Map<String, Double> categoryTotals = new HashMap<>();
        for (PaymentEntity payment : payments) {
            categoryTotals.merge(payment.getCategoryName(), payment.getAmount().doubleValue(), Double::sum);
        }

        return categoryTotals.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() / totalAmount) * 100
                ));
    }

    /**
     * 각 카드별 소비 금액을 계산합니다.
     * @param memberId 사용자 ID
     * @return 카드별 소비 금액 (카드명, 금액)
     */
    public Map<String, Double> getCardSpendingData(Integer memberId) {
        LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        List<PaymentEntity> payments = paymentRepository.findByMemberIdAndDatetimeBetween(memberId, start, end);
        System.out.println("Payments Retrieved: " + payments);

        Map<String, Double> cardTotals = new HashMap<>();
        for (PaymentEntity payment : payments) {
        	 System.out.println("Processing Payment: " + payment);
        	String cardName = payment.getCardName() != null ? payment.getCardName() : "기타";
        	cardTotals.merge(cardName, payment.getAmount().doubleValue(), Double::sum);
//            cardTotals.merge(payment.getCardName(), payment.getAmount().doubleValue(), Double::sum);
        }
        
        System.out.println("Card Totals: " + cardTotals);

        return cardTotals;
    }

    /**
     * 최근 7일 동안의 일일 소비 금액을 반환합니다.
     * @param memberId 사용자 ID
     * @return 최근 7일 소비 추이 (날짜, 금액)
     */
//    public Map<String, Double> getLast7DaysSpendingTendency(Integer memberId) {
//        LocalDateTime end = LocalDate.now().atStartOfDay();
//        LocalDateTime start = end.minusDays(7);
//
//        List<PaymentEntity> payments = paymentRepository.findByMemberIdAndDatetimeBetween(memberId, start, end);
//
//        Map<LocalDate, Double> dailySpending = new HashMap<>();
//        for (PaymentEntity payment : payments) {
//            LocalDate date = payment.getDatetime().toLocalDate();
//            dailySpending.merge(date, payment.getAmount().doubleValue(), Double::sum);
//        }
//
//        // Ensure all 7 days are present in the map
//        for (int i = 0; i < 7; i++) {
//            LocalDate date = start.toLocalDate().plusDays(i);
//            dailySpending.putIfAbsent(date, 0.0);
//        }
//
//        return dailySpending.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .collect(Collectors.toMap(
//                        entry -> entry.getKey().toString(),
//                        Map.Entry::getValue,
//                        (a, b) -> b,
//                        LinkedHashMap::new
//                ));
//    }
//
//    /**
//     * 가장 많이 소비한 날과 월 평균 소비를 반환합니다.
//     * @param memberId 사용자 ID
//     * @return 비교 데이터 (mostSpentDay: 금액, averageDaily: 금액)
//     */
//    public Map<String, Double> getDayComparisonData(Integer memberId) {
//        LocalDateTime start = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay();
//        LocalDateTime end = start.plusMonths(1);
//
//        List<PaymentEntity> payments = paymentRepository.findByMemberIdAndDatetimeBetween(memberId, start, end);
//
//        Map<LocalDate, Double> dailyTotals = new HashMap<>();
//        for (PaymentEntity payment : payments) {
//            LocalDate date = payment.getDatetime().toLocalDate();
//            dailyTotals.merge(date, payment.getAmount().doubleValue(), Double::sum);
//        }
//
//        double mostSpentDay = dailyTotals.values().stream()
//                .max(Double::compareTo)
//                .orElse(0.0);
//
//        double totalSpending = dailyTotals.values().stream().mapToDouble(Double::doubleValue).sum();
//        long totalDays = dailyTotals.size();
//        double averageDaily = totalDays > 0 ? totalSpending / totalDays : 0.0;
//
//        Map<String, Double> comparisonData = new HashMap<>();
//        comparisonData.put("mostSpentDay", mostSpentDay);
//        comparisonData.put("averageDaily", averageDaily);
//
//        return comparisonData;
//    }
}
