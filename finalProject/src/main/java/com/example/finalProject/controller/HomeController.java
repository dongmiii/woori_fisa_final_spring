package com.example.finalProject.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import com.example.finalProject.domain.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.finalProject.domain.entity.News2Entity;
import com.example.finalProject.domain.entity.NewsEntity;
import com.example.finalProject.domain.repository.MemberRepository;
import com.example.finalProject.dto.MemberDTO;
import com.example.finalProject.dto.MemberResponseDTO;
import com.example.finalProject.service.FastApiService;
import com.example.finalProject.service.MemberService;
import com.example.finalProject.service.News2Service;
import com.example.finalProject.service.NewsService;
import com.example.finalProject.validate.CheckEmailValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private final NewsService newsService;
	private final News2Service news2Service;



	// 메인페이지
	@GetMapping("/")
	public String Home(Model model) {
		List<NewsEntity> newsList = newsService.getAllNews(); //entity News
		List<News2Entity> news2List = news2Service.getAllNews();
		model.addAttribute("newsList", newsList);
		model.addAttribute("news2List", news2List);
		return "index";
	}

//	@GetMapping("/test")
//	public String test() {
//		return "layout_test";
//	}

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
			Long userId = memberService.getAutoIncrementIdByEmail(user);
			String userName = memberService.getUsernameByEmail(user);

			// 조회된 ID와 사용자 이름을 세션에 저장
			session.setAttribute("userid", userId);
			System.out.println("Authenticated autoID: " + userId);

			session.setAttribute("username", userName);
			System.out.println("*** username: " + userName);

			// 사용자 엔티티에서 추가 정보 가져오기
			MemberEntity member = memberService.findByEmail(user); // 이메일로 사용자 조회
			if (member != null) {
				// 사용자 이미지 처리
				if (member.getImage() != null) {
					// 이미지가 BLOB으로 저장된 경우 Base64 인코딩
					String base64Image = Base64.getEncoder().encodeToString(member.getImage());
					session.setAttribute("userImage", "data:image/png;base64," + base64Image);
					System.out.println("User image (Base64) stored in session.");
				} else if (member.getImageUrl() != null) {
					// 이미지 URL이 있는 경우 세션에 저장
					session.setAttribute("userImage", member.getImageUrl());
					System.out.println("User image URL stored in session: " + member.getImageUrl());
				} else {
					System.out.println("User has no image.");
				}
			} else {
				System.err.println("No member found for email: " + user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred while processing user information.");
		}

		return "redirect:/";
	}
//	@GetMapping("/login/result")
//	public String dispLoginResult(Model model, HttpSession session) {
//		// 현재 사용자의 인증 상태 확인
//		String user = SecurityContextHolder.getContext().getAuthentication().getName();
//
//		if (user == null || "anonymousUser".equals(user)) {
//			System.out.println("User not authenticated");
//			return "redirect:/login";
//		}
//
//		System.out.println("Authenticated userId: " + user);
//
//		// 세션에 userId 저장
//		if (session.getAttribute("usermail") == null) {
//			session.setAttribute("usermail", user);
//		}
//
////		model.addAttribute("userId", userName);
//
//		try {
//
//			Long userId = memberService.getAutoIncrementIdByEmail(user);
//			String userName = memberService.getUsernameByEmail(user);
//
//			// 조회된 ID를 세션에 저장
//			session.setAttribute("userid", userId);
//			System.out.println("Authenticated autoID: " + userId);
//
//			session.setAttribute("username", userName);
//			System.out.println("*** username: " + userName);
//
//
//			// FastAPI 호출
//			String fastApiResponse = fastApiService.sendname(userName);
//
//			// FastAPI 호출 결과 처리
//			if (fastApiResponse != null) {
//				System.out.println("FastAPI Responsed");
//
//				// FastAPI에서 받은 데이터를 JSON 형식으로 파싱하여 모델에 추가
//				String prompt = extractPromptFromResponse(fastApiResponse);
//				String image = extractImageFromResponse(fastApiResponse);
//
//				session.setAttribute("fastApiPrompt", prompt);
//				session.setAttribute("fastApiImage", image);
//			} else {
//				System.err.println("FastAPI did not return a valid response.");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println("Error occurred while calling FastAPI.");
//		}
//
//		return "redirect:/";
//	}





	private String extractPromptFromResponse(String fastApiResponse) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> responseMap = objectMapper.readValue(fastApiResponse, Map.class);
			return responseMap.get("prompt");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private String extractImageFromResponse(String fastApiResponse) {
		try {
			// JSON 응답을 Map으로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> responseMap = objectMapper.readValue(fastApiResponse, Map.class);
			return responseMap.get("image"); // "image" 키에 해당하는 값 반환
		} catch (Exception e) {
			e.printStackTrace();
			return null; // 에러 발생 시 null 반환
		}
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

	@GetMapping("/members")
	public String members(Model model) {
		List<MemberResponseDTO> members = memberService.findMembers();
		model.addAttribute("members",members);

		return "/members/memberList";
	}

	@GetMapping("/category/store")
	public String store() {
		return "/category/store";
	}

	@GetMapping("/category/cafe")
	public String cafe() {
		return "/category/cafe";
	}

	@GetMapping("/category/oil")
	public String oil() {
		return "category/oil";
	}

	@GetMapping("/category/movie")
	public String movie() {
		return "/category/movie";
	}

	@GetMapping("/category/mart")
	public String mart() {
		return "/category/mart";
	}


	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		try {
			log.info("Dashboard controller called");
			return "/dashboard/dashboard";
		} catch (Exception e) {
			log.error("Error in dashboard controller", e);
			return "error";
		}
	}

	@GetMapping("/calendar")
	public String calendar() {
		return "/calendar/calendar"; // calendar.html로 매핑
	}
	@GetMapping("/card")
	public String cardChatbot() {
		return "/card/chatbot"; // calendar.html로 매핑
	}
}