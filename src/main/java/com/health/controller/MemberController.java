package com.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
	@GetMapping(value = "/memberForm")
	public String order() {
		
		return "/member/memberForm";
	}
	
	@GetMapping(value = "/loginForm")
	public String login() {
		
		return "/member/loginForm";
	}
}





