package com.example.finalProject.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.finalProject.domain.entity.MemberEntity;
import com.example.finalProject.domain.repository.MemberRepository;
import com.example.finalProject.dto.MemberDTO;
import com.example.finalProject.dto.MemberResponseDTO;
import com.example.finalProject.service.FastApiService;
import com.example.finalProject.service.FinanceService;
import com.example.finalProject.service.LifeService;
import com.example.finalProject.service.MemberService;
import com.example.finalProject.validate.CheckEmailValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final CheckEmailValidator checkEmailValidator;
	private final FastApiService FastApiService;
	private final FinanceService financeService;
	private final LifeService lifeService;



	@GetMapping("/loginMain")
	public String loginMain() {
		return "members/loginMain"; //view
	}

	// 로그인페이지
	@GetMapping("/login")
	public String login() {
		return "members/login";
	}

	@Autowired
	private FastApiService fastApiService;

	//	로그인 결과
	@GetMapping("/login/result")
	public String dispLoginResult(Model model, HttpSession session) {
		// 현재 사용자의 인증 상태 확인
		String user = SecurityContextHolder.getContext().getAuthentication().getName();

		if (user == null || "anonymousUser".equals(user)) {
			System.out.println("User not authenticated");
			return "redirect:/login";
		}

		System.out.println("Authenticated userId: " + user);

		// 세션에 usermail 저장
		if (session.getAttribute("usermail") == null) {
			session.setAttribute("usermail", user);
		}

		try {
			// 사용자 정보 가져오기
			int userId = memberService.getAutoIncrementIdByEmail(user);
			String userName = memberService.getUsernameByEmail(user);
			String prompt = memberService.getPromptByEmail(user);

			int honey = memberService.getHoneyByEmail(user);

			// 조회된 ID와 사용자 이름을 세션에 저장
			session.setAttribute("userid", userId);
			System.out.println("Authenticated autoID: " + userId);

			session.setAttribute("username", userName);
			System.out.println("*** username: " + userName);

			session.setAttribute("prompt", prompt);
			System.out.println("*** prompt: " + prompt);

			if (prompt != null && !prompt.isEmpty()) {
				List<String> promptLines = Arrays.stream(prompt.split("\n"))
						.map(String::trim) // 각 줄의 공백 제거
						.filter(line -> !line.isEmpty()) // 빈 줄 제외
						.toList();
				session.setAttribute("promptLines", promptLines);
			} else {
				session.setAttribute("promptLines", Collections.emptyList());
			}

			session.setAttribute("honey", honey);
			System.out.println("*** honey: " + honey);

			// 사용자 엔티티에서 추가 정보 가져오기
			MemberEntity member = memberService.findByEmail(user); // 이메일로 사용자 조회
			if (member != null) {
				// 사용자 이미지 처리
				if (member.getImage() != null) {
					// 이미지가 BLOB으로 저장된 경우 Base64 인코딩
					String base64Image = Base64.getEncoder().encodeToString(member.getImage());
					session.setAttribute("userImage", "data:image/png;base64," + base64Image);
					System.out.println("User image (Base64) stored in session.");
				} else {
					session.setAttribute("userImage", null); // 이미지 없음 처리
					System.out.println("User has no image.");
				}
				
				if(member.getPrompt() == null || member.getPrompt().isEmpty()) {
					System.out.println("Redirecting to roulette.html because Prompt is empty");
					return "redirect:/roulette/roulette";
				}
				
			} else {
				session.setAttribute("userImage", null); // 사용자 없음 처리
				System.err.println("No member found for email: " + user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred while processing user information.");
		}

		return "redirect:/";
	}

	@GetMapping("/")
	public String Home(@RequestParam(value = "forceRefresh", required = false) String forceRefresh,
					   HttpSession session,
					   Model model) {
		// 특정 세션 속성 초기화 로직
		if ("true".equals(forceRefresh)) {
			// 기존 세션 속성 제거
			session.removeAttribute("userImage");
			session.removeAttribute("prompt");
			// 새로운 데이터를 불러오기
			String userEmail = (String) session.getAttribute("usermail"); // 세션에서 사용자 이메일 가져오기
			
			if (userEmail != null) {
				// 사용자 정보 가져오기
				MemberEntity member = memberService.findByEmail(userEmail); // 이메일로 사용자 조회
				if (member != null) {
					// 이미지 처리
					if (member.getImage() != null) {
						// 이미지가 BLOB으로 저장된 경우 Base64 인코딩
						String base64Image = Base64.getEncoder().encodeToString(member.getImage());
						session.setAttribute("userImage", "data:image/png;base64," + base64Image);
					} else if (member.getImageUrl() != null) {
						// URL로 저장된 이미지 처리
						session.setAttribute("userImage", member.getImageUrl());
					} else {
						session.setAttribute("userImage", null); // 이미지 없음 처리
					}

					// prompt 값 불러오기
					String prompt = memberService.getPromptByEmail(userEmail);
					if (prompt != null && !prompt.isEmpty()) {
						List<String> promptLines = Arrays.stream(prompt.split("\n"))
								.map(String::trim) // 각 줄의 공백 제거
								.filter(line -> !line.isEmpty()) // 빈 줄 제외
								.toList();
						session.setAttribute("promptLines", promptLines);
					} else {
						session.setAttribute("promptLines", Collections.emptyList());
					}

	                // 최신 honey 값 가져와 세션에 저장
	                int updatedHoney = member.getHoney();
	                session.setAttribute("honey", updatedHoney);
	                System.out.println("Updated honey value in session: " + updatedHoney);
				}
			}

			return "redirect:/"; // 파라미터 제거를 위해 리다이렉트
		}
		
		
	    // 세션에서 현재 값을 가져와 모델에 추가
	    String userEmail = (String) session.getAttribute("usermail");
	    if (userEmail != null) {
	        int currentHoney = (int) session.getAttribute("honey");
	        model.addAttribute("honey", currentHoney);
	    }
		
		return "index";
	}


	@GetMapping("/logout/result")
	public String dispLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if(session != null) {
			session.invalidate();
		}

		System.out.println("logout");

		return "redirect:/";
	}


	// 회원가입 페이지
	@GetMapping("/join")
	public String createMemberForm(Model model, MemberDTO memberdto) {
		// addAttribute ; 모델에 값을 지정
		model.addAttribute("formData", memberdto); // Member 객체 저장
		return "members/join"; // view
	}

	// 커스텀 유효성 검증을 위해 추가
	@InitBinder // 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkEmailValidator);
	}

	// 회원가입 처리
	@PostMapping("/join/result")
	public String createMember(@Valid MemberDTO memberdto, Errors errors, Model model) {

		model.addAttribute("formData",memberdto);

		if(errors.hasErrors()) { // 유효성 검사에 실패한 필드가 있는지 확인
			// memberdto를 담아줘서 회원가입 실패 시, 회원가입 페이지에서 입력했던 정보들을 그대로 유지하기 위해 입력받았던 데이터를 그대로 할당해줌
			model.addAttribute("memberdto", memberdto);

			// 유효성 검사에 실패한 필드가 있다면, Service 계층으로 Errors 객체를 전달해 비지니스 로직을 구현하고 모델에 담음
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}

			return "members/join";
		}

		memberService.join(memberdto);
		return "members/login";
	}

//	@GetMapping("/members")
//	public String members(Model model) {
//		List<MemberResponseDTO> members = memberService.findMembers();
//		model.addAttribute("members",members);
//
//		return "/members/memberList";
//	}

	@GetMapping("/category/store")
	public String store() {
		return "category/store";
	}

	@GetMapping("/category/cafe")
	public String cafe() {
		return "category/cafe";
	}

	@GetMapping("/category/oil")
	public String oil() {
		return "category/oil";
	}

	@GetMapping("/category/movie")
	public String movie() {
		return "category/movie";
	}

	@GetMapping("/category/mart")
	public String mart() {
		return "category/mart";
	}


	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		try {
			log.info("Dashboard controller called");
			return "dashboard/dashboard";
		} catch (Exception e) {
			log.error("Error in dashboard controller", e);
			return "error";
		}
	}

	@GetMapping("/calendar")
	public String calendar() {
		return "calendar/calendar"; // calendar.html로 매핑
	}
	
	@GetMapping("/card")
	public String cardChatbot() {
		return "card/chatbot"; // chatbot.html로 매핑
	}
}