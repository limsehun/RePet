package edu.kh.repet.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.repet.main.service.MainService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({ "loginMember" })
@Controller // 요청 / 응답 제어 하는 역할 명시 + Bean 등록
@RequiredArgsConstructor
public class MainController {

	private final MainService service;

	@RequestMapping("/") // "/" 요청 매핑(method 가리지 않음)
	public String mainPage() {
		return "common/main";
	}


}
