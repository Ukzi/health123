package com.health.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/thymeleaf")
public class TestController {
	
	@GetMapping(value = "/ex01")
	public String test(Model model) {
		model.addAttribute("data", "타임리프 예제 입니다.");
		return "test/ex01";
	}
}
