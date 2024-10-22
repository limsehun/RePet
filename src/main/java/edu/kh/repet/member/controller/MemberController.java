package edu.kh.repet.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@SessionAttributes("loginMember")
@Controller // 요청 / 응답 제어 하는 역할 명시 + Bean 등록(IOC)
@RequestMapping("member") // /member로 시작하는 요청 매핑
@Slf4j // log 필드 자동 생성 Lombok 어노테이션
public class MemberController {
	
	private final MemberService service;
	
	@PostMapping("login")
	public String loginModal(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			RedirectAttributes ra,
			Model model,
			HttpServletResponse resp
			) {
		
		Member loginMember = service.login(email, password);
		
		
		if(loginMember == null) { // 로그인 실패
			ra.addFlashAttribute("message", "이메일 또는 비밀번호가 일치하지 않습니다");
		}
		else {
			model.addAttribute("loginMember", loginMember);
		}
		
		return "login";
	}

}
