package edu.kh.repet.email.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.repet.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender mailSender; 
	// EmailConfig 내용이 적용된 이메일 발송이 가능한 객체(Bean)
	
	private final RedisUtil redisUtil;
	// Redis(InMemory DB) CRUD 할 수 있는 기능을 제공하는 객체(Bean)

	@Override
	public int sendPassword(String string, String email) {
		// TODO Auto-generated method stub
		return 0;
	}
}
