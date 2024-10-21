package edu.kh.repet.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.member.service.MemberService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService service;
	
	@ResponseBody
	public String login(
			@RequestBody String username, 
			@RequestBody String password) {
		
		return "common/main";
	}

}
