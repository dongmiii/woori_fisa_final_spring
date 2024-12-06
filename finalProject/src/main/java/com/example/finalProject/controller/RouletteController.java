package com.example.finalProject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.finalProject.service.MemberService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/roulette")
public class RouletteController {
	
	private MemberService memberService;
	
	public RouletteController(MemberService memberService) {
		this.memberService = memberService;
	}
	
    @GetMapping("/roulette")
    public String roulette() {
    	return "roulette/roulette"; // templates/roulette/roulette.html
    }

//    @GetMapping("/roulette")
//    public String roulette(Model model, @RequestParam(value = "usermail", required = false, defaultValue = "") String userMail) {
//        if (userMail.isEmpty()) {
//            model.addAttribute("honey", 0); // 기본값 설정
//        } else {
//            int honey = memberService.getHoneyByEmail(userMail);
//            model.addAttribute("honey", honey);
//        }
//    	return "roulette/roulette"; // templates/roulette/roulette.html
//    }

    
    @PostMapping("/checkHoneyAndSpin")
    @ResponseBody
    public Map<String, Object> checkHoneyAndSpin(@RequestParam("userid") int userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 현재 honey 점수 가져오기
            int currentHoney = memberService.getHoneyById(userId);

            if (currentHoney < 5) {
                response.put("success", false);
                response.put("message", "꿀이 부족합니다. 룰렛을 돌릴 수 없습니다!");
                response.put("currentHoney", currentHoney); // 현재 honey 값 반환
            } else {
                // 5점 차감
                memberService.subtractHoney(userId, 5);

                response.put("success", true);
                response.put("message", "룰렛을 돌릴 수 있습니다!");
                response.put("currentHoney", currentHoney - 5); // 차감된 honey 값 반환
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다. 다시 시도해주세요.");
            e.printStackTrace(); // 디버깅을 위해 예외 로그 출력
        }
        
        
        return response;
    }
    
    
    
    
    @GetMapping("/loading")
    public String loading(@RequestParam("character") String character, Model model) {
        model.addAttribute("character", character);
        return "roulette/loading"; // templates/roulette/loading.html
    }
    
//    @PostMapping("/checkHoneyAndSpin")
//    @ResponseBody
//    public Map<String, Object> checkHoneyAndSpin(@RequestParam("usermail") String userMail){
//        
//    	Map<String, Object> response = new HashMap<>();
//    	
//    	try {
//            int currentHoney = memberService.getHoneyByEmail(userMail);
//
//            if (currentHoney < 5) {
//                response.put("success", false);
//                response.put("message", "꿀이 부족합니다. 룰렛을 돌릴 수 없습니다!");
//                response.put("currentHoney", currentHoney);
//            }
//            else {
//                memberService.subtractHoney(userMail, 5);
//                response.put("success", true);
//                response.put("message", "룰렛을 돌릴 수 있습니다!");
//                response.put("currentHoney", memberService.getHoneyByEmail(userMail));
//            }
//
//        } catch (Exception e) {
//            response.put("success", false);
//            response.put("message", "서버 오류가 발생했습니다. 다시 시도해주세요.");
//        }
//        return response;
//    }
    
    
}