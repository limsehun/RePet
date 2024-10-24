package edu.kh.repet.email.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.repet.common.util.RedisUtil;
import edu.kh.repet.email.service.EmailService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {
	

	public RedisUtil redisUtil;
	

	public EmailService service;
	
	// 레디스 확인하기
	@ResponseBody
	@GetMapping("redisTest")
	public int redisTest(
			@RequestParam("key") String key,
			@RequestParam("value") String value) {
		
		// 전달 받은 key, value를 redis에 set 하기
		redisUtil.setValue(key, value, 60); // 60초 후에 만료
		
		return 1;
	}
	
	/** 인증 번호 발송
	 * @param email : 입력된 이메일
	 * @return 성공 1, 실패 0
	 */
	@ResponseBody
	@PostMapping("sendPassword")
	public int sendPassword(@RequestBody String email) {
		
		return service.sendPassword("signUp", email);
	}
	

	
}