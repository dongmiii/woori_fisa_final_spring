package com.example.finalProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalProject.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/honey")
public class HoneyController {
	
	private final MemberService memberService;
	
	public HoneyController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	// honey 점수 업데이트(내역 추가 및 평가 시)
	@PostMapping("/honeyUpdate")
	public int updateHoney(@RequestParam("date") String date, HttpSession session) {
        String userEmail = (String) session.getAttribute("usermail");
        if (userEmail == null) {
            throw new IllegalStateException("사용자가 인증되지 않았습니다.");
        }
        
        // 하루에 한 번만 점수 추가
        boolean isUpdated = memberService.updateHoneyForDate(userEmail,date);
        
        if(isUpdated) {
        	// 업데이트 된 honey 값을 반환하여 클라이언트에 반영
        	int updateHoney = memberService.getHoneyByEmail(userEmail);
        	session.setAttribute("honey", updateHoney);
        	
        	return updateHoney;
        }else {
        	// 이미 해당 날짜에 점수가 추가된 경우 기존 점수를 반환
        	return (int) session.getAttribute("honey");
        }
	}

}
