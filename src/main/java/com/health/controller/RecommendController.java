package com.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/recommendation")
@Controller
@RequiredArgsConstructor
public class RecommendController {
	@GetMapping(value = "/recommendProgram")
	public String order() {
		
		return "/recommendation/recommendProgram";
	}
}
