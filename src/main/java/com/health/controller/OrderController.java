package com.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/order")
@Controller
@RequiredArgsConstructor
public class OrderController {
	
	@GetMapping(value = "/orderPage")
	public String order() {
		
		return "/order/orderPage";
	}
}
