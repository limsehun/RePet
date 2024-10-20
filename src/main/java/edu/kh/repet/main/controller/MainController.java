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

	@PostMapping("/login")
	@ResponseBody
	public String login(
			@RequestBody String username, 
			@RequestBody String password) {
		
		if (username.equals("admin") && password.equals("password")) {
			return "로그인 성공!";
		} else {
			return "로그인 실패. 사용자 이름 또는 비밀번호를 확인하세요.";
		}

	}
}
