package edu.kh.repet.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
@Controller // 요청 / 응답 제어 하는 역할 명시 + Bean 등록(IOC)
@RequestMapping("member") // /member로 시작하는 요청 매핑
@Slf4j // log 필드 자동 생성 Lombok 어노테이션
public class MemberController {
	
	private final MemberService service;
	
	@PostMapping("login")
	public String loginModal(
			@RequestParam("lgEmail") String memberEmail,
			@RequestParam("lgPassword") String memberPw,
			RedirectAttributes ra,
			Model model,
			HttpServletResponse resp
			) {
		
		Member loginMember = service.login(memberEmail, memberPw);
		
		
		
		if(loginMember == null) { // 로그인 실패
			ra.addFlashAttribute("message", 
					"이메일 또는 비밀번호가 일치하지 않습니다");
		}
		else {
			model.addAttribute("loginMember", loginMember);
		}
		
		return "redirect:/";
	}
	
	
	
	@GetMapping("signUp")
	public String signUp() {
		
		return "common/main";
	}
	
	
	/** 회원 가입 수행
	 * @param inputMember : 입력값이 저장된 Member 객체(커맨드 객체)
	 * @param ra					: 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("signUp")
	public String signUp(
			@ModelAttribute Member inputMember,
			RedirectAttributes ra) {
		
		// 회원 가입 서비스 호출
		int result = service.signUp(inputMember);
		
		
		// 서비스 결과에 따라 응답 제어
		String path = null;
		String message = null;
		
		if(result > 0) {
			message = inputMember.getMemberNickname() + "님이 가입을 환영합니다";
			path = "redirect:/";
		}else {
			message = "회원 가입 실패...";
			path = "redirect:/";
		}
		
		ra.addFlashAttribute("message", message);		
		
		return path;
	}
	
	
	
	
	/** 로그 아웃
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		
		return "redirect:/"; // 메인 페이지
	}
	

		@ResponseBody
		@GetMapping("memberEmailCheck")
		public int emailCheck(
				@RequestParam("memberEmail") String memberEmail) {
			
			return service.emailCheck(memberEmail);
		}
		
		@ResponseBody
		@GetMapping("passwordCheck")
		public int pwCheck(
				@RequestParam("memberPw") String memberPw) {
			
			return service.pwCheck(memberPw);
		}
		
		
		@ResponseBody
		@GetMapping("nicknameCheck")
		public int nicknameCheck(
				@RequestParam("memberNickname") String memberNickname) {
			
			return service.nicknameCheck(memberNickname);
		}
		
		
}
