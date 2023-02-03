package com.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/program")
@Controller
@RequiredArgsConstructor
public class ProgramController {
	@GetMapping(value = "/programPage")
	public String order() {
		
		return "/program/programPage";
	}
}
