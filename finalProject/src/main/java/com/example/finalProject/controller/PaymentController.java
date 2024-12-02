package com.example.finalProject.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalProject.domain.entity.PaymentEntity;
import com.example.finalProject.domain.repository.PaymentRepository;
import com.example.finalProject.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    public PaymentController(PaymentRepository paymentRepository, MemberService memberService) {
        this.paymentRepository = paymentRepository;
        this.memberService = memberService; 
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
//    @GetMapping("/total")
//    public Long getTotalAmountByDate(@RequestParam("date") String date) {
//    	 LocalDate selectedDate = LocalDate.parse(date); 
//    	 return paymentRepository.calculateTotalAmount(
//    			 selectedDate.atStartOfDay(),
//    			 selectedDate.plusDays(1).atStartOfDay()
//    			 );
//    }
    
    @GetMapping("/total")
    public Long getTotalAmountByDateAndUser(HttpSession session, @RequestParam("date") String date) {
        // 세션에서 로그인한 사용자의 member_id 가져오기
        Object sessionUserId = session.getAttribute("userid");
        if (sessionUserId == null) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Integer memberId = (Integer) sessionUserId;

        // 선택된 날짜 범위 생성
        LocalDate selectedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = selectedDate.atStartOfDay();
        LocalDateTime endOfDay = selectedDate.plusDays(1).atStartOfDay();

        // 사용자 ID와 날짜 범위로 총 금액 계산
        return paymentRepository.calculateTotalAmountForUser(memberId, startOfDay, endOfDay);
    }
    
    
    @GetMapping("/totalUser")
    public List<PaymentEntity> getPaymentsByDateAndUser(HttpSession session, 
                                                        @RequestParam("date") String date) {
        Object sessionUserId = session.getAttribute("userid");
        if (sessionUserId == null) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Integer memberId = (Integer) sessionUserId;

        // 선택된 날짜 범위 생성
        LocalDate selectedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = selectedDate.atStartOfDay();
        LocalDateTime endOfDay = selectedDate.plusDays(1).atStartOfDay();

        // 사용자 ID와 날짜 범위로 데이터 조회
        return paymentRepository.findByMemberIdAndDatetimeBetween(memberId, startOfDay, endOfDay);
    }
    
    @GetMapping("/user")
    public List<PaymentEntity> getPayments(HttpSession session) {
        Object sessionUserId = session.getAttribute("userid");
        if (sessionUserId == null) {
            System.out.println("getPayments / memberID is null");
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Integer memberId;
        if (sessionUserId instanceof Integer) {
            memberId = (Integer) sessionUserId;
        } else {
            throw new ClassCastException("Invalid type for session attribute 'userid'.");
        }

        System.out.println("*getPayments***memberId :::: " + memberId);

        // 해당 member_id로 결제 내역 조회
        List<PaymentEntity> payments = paymentRepository.findByMemberId(memberId);
        System.out.println("결제 내역 개수: " + payments.size());
        payments.forEach(payment -> System.out.println("결제 내역: " + payment));

        return payments;
    }
    
    
//    @PostMapping("/user")
//    public PaymentEntity addPayment(@RequestBody PaymentEntity payment, HttpSession session) {
//    	Integer memberId =  (Integer) session.getAttribute("userid");
//    	System.out.println("***addPayment **memberiD :::: " +  memberId);
//    	
//    	if(memberId == null) {
//    		System.out.println("addPayment / memberID is null");
//    	}
//    	
//    	payment.setMemberId(memberId);
//    	
//    	return paymentRepository.save(payment);
//    }
    
    @PostMapping("/user")
    public PaymentEntity addPayment(@RequestBody PaymentEntity payment, HttpSession session) {
        // 세션에서 로그인한 사용자의 member_id 가져오기
        Object sessionUserId = session.getAttribute("userid");
        if (sessionUserId == null) {
            System.out.println("addPayment / memberID is null");
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Integer memberId;
        if (sessionUserId instanceof Integer) {
            memberId = (Integer) sessionUserId; // 그대로 사용
        } else if (sessionUserId instanceof Long) {
            memberId = ((Long) sessionUserId).intValue(); // Long을 Integer로 변환
        } else {
            throw new ClassCastException("Invalid type for session attribute 'userid'.");
        }

        System.out.println("***addPayment ***memberId :::: " + memberId);

        // 결제 내역에 사용자의 memberId 설정
        payment.setMemberId(memberId);
        
        // 특정 날짜에 이미 내역을 추가했는지 확인
        LocalDate today = LocalDate.now();
        boolean alreadyAdd = paymentRepository.existsByMemberIdAndDatetimeBetween(
                memberId, today.atStartOfDay(), today.plusDays(1).atStartOfDay());

        if(!alreadyAdd) {
        	memberService.addHoney(memberId, 1);
        }
        
        // 데이터 저장
        return paymentRepository.save(payment);
    }
    
    @PostMapping("/feedback")
    public void addFeedback(HttpSession session) {
    	Object sessionUserId = session.getAttribute("userid");
    	if(sessionUserId == null) {
    		throw new IllegalStateException("addFeedback :: 로그인된 사용자 없음");
    	}
    	
    	Integer memberId = (Integer) sessionUserId;
    	memberService.addHoney(memberId, 1);
    }
    
    @GetMapping("/honey")
    public Integer getHoney(HttpSession session) {
        Object sessionUsermail = session.getAttribute("usermail");
        if (sessionUsermail == null) {
            throw new IllegalStateException("getHoney :: 로그인된 사용자가 없습니다.");
        }
        String memberMail = (String) sessionUsermail;
        return memberService.getHoneyByEmail(memberMail);
    }
    
    
    
}