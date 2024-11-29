package com.example.finalProject.controller;

import com.example.finalProject.domain.entity.PaymentEntity;
import com.example.finalProject.domain.repository.PaymentRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 모든 결제 내역 가져오기
    @GetMapping
    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 새 결제 내역 추가
    @PostMapping
    public PaymentEntity createPayment(@RequestBody PaymentEntity payment) {
    	System.out.println("새로운 결제 내역이 추가됨 : " + payment);
    	return paymentRepository.save(payment);
    }

    // 결제 내역 삭제
    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable String id) {
//        paymentRepository.deleteById(id);
    }

    // 특정 날짜의 결제 내역 가져오기
    @GetMapping("/date")
    public List<PaymentEntity> getPaymentsByDate(@RequestParam("date") String date) {
        LocalDate selectedDate = LocalDate.parse(date); // 요청된 날짜 파싱
        return paymentRepository.findByDatetime(selectedDate.atStartOfDay(), selectedDate.plusDays(1).atStartOfDay());
    }
    
    // 특정 날짜의 소비 총 금액 가져오기
    @GetMapping("/total")
    public Long getTotalAmountByDate(@RequestParam("date") String date) {
    	 LocalDate selectedDate = LocalDate.parse(date); 
    	 return paymentRepository.calculateTotalAmount(
    			 selectedDate.atStartOfDay(),
    			 selectedDate.plusDays(1).atStartOfDay()
    			 );
    }
    
    @GetMapping("/user")
    public List<PaymentEntity> getPayments(HttpSession session){
    	// 세션에서 로그인한 사용자의 member_id 가져오기
    	Long memberId = (Long) session.getAttribute("userid");
    	System.out.println("*getPayments***memberiD :::: " +  memberId);
    	
    	if(memberId == null) {
    		System.out.println("getPayments / memberID is null");
    	}
    	
    	// 해당 member_id로 결제 내역 조회
    	List<PaymentEntity> payments = paymentRepository.findByMemberId(memberId);
    	return payments;
    }
    
    @PostMapping("/user")
    public PaymentEntity addPayment(@RequestBody PaymentEntity payment, HttpSession session) {
    	Integer memberId =  (Integer) session.getAttribute("userid");
    	System.out.println("***addPayment **memberiD :::: " +  memberId);
    	
    	if(memberId == null) {
    		System.out.println("addPayment / memberID is null");
    	}
    	
    	payment.setMemberId(memberId);
    	
    	return paymentRepository.save(payment);
    }
    
}