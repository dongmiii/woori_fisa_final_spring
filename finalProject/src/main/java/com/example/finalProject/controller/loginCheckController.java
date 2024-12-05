package com.example.finalProject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class loginCheckController {

	@GetMapping("/check")
	public boolean checkLoginStatus(HttpSession session) {
		return session.getAttribute("userid") != null;
	}
}
