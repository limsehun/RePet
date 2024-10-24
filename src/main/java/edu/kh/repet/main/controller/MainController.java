package edu.kh.repet.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import edu.kh.repet.main.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService service;
	
	@GetMapping("/")
	public String Main() {
		
		return "common/main";
	}
	
}
