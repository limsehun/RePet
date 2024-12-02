package edu.kh.repet.email.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.w3c.dom.Document;

import edu.kh.repet.common.util.RedisUtil;
import edu.kh.repet.email.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final BCryptPasswordEncoder encoder;

	private final EmailMapper mapper;

	private final JavaMailSender mailSender;
	// EmailConfig 내용이 적용된 이메일 발송이 가능한 객체(Bean)

	private final RedisUtil redisUtil;
	// Redis(InMemory DB) CRUD 할 수 있는 기능을 제공하는 객체(Bean)

	private final SpringTemplateEngine templateEngine;
	// 자바에서 타임리프를 사용할 수 있게하는 객체(Bean)
	// html코드를 Java로 읽어올 수 있음

	@Override
	public int sendNewPw(String htmlName, String email) {
		try {

			String emailTitle = null; // 발송되는 이메일 제목
			String newPw = createNewPw(); // 생성되는 새 비밀번호

			// 이메일 발송 시 사용할 html 파일의 이름에 따라
			// 이메일 제목, 내용을 다르게 설정
			switch (htmlName) {

			case "login":
				emailTitle = "REPET의 새로운 비밀번호 입니다.";
				break;
			}

			// MimeMessage : 메일 발송 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			// MimeMessageHelper :
			// Spring에서 제공하는 메일 발송 도우미

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setTo(email); // 받는 사람 이메일 세팅
			helper.setSubject(emailTitle); // 이메일 제목 세팅

			helper.setText(loadHtml(newPw, htmlName), true); // 이메일 내용 세팅

//			helper.addInline("logo", new ClassPathResource("static/images/logo.png"));

			// 메일 발송하기
			mailSender.send(mimeMessage);

			// Redis에 이메일,새로운 비밀번호(5분 후 만료)
			redisUtil.setValue(email, newPw, 60 * 5);

			String encPw = encoder.encode(newPw);

			int result = mapper.updatePw(email, encPw);

		} catch (Exception e) {
			e.printStackTrace();
			return 0; // 예외 발생 == 실패 == 0 반환
		}

		return 1; // 예외 발생 X == 성공 == 1 반환
	}

	
	
	// --------------- 탈퇴 시킬 시 이메일 보내기 --------------------
	
	@Override
	public int secessionMessage(String htmlName, String email) {
		try {
			
			String emailTitle = null; // 발송되는 이메일 제목
			String memberDelFl = "N";

			// 이메일 발송 시 사용할 html 파일의 이름에 따라
			// 이메일 제목, 내용을 다르게 설정
			switch (htmlName) {
			
			case "manager-info":
				emailTitle = "경고 누적으로 인해 탈퇴 되었습니다";
				break;
			}
			
			// MimeMessage : 메일 발송 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			// MimeMessageHelper :
			// Spring에서 제공하는 메일 발송 도우미
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setTo(email); // 받는 사람 이메일 세팅
			helper.setSubject(emailTitle); // 이메일 제목 세팅
			
			helper.setText(loadHtml(memberDelFl, htmlName), true); // 이메일 내용 세팅
			
//			helper.addInline("logo", new ClassPathResource("static/images/logo.png"));
			
			// 메일 발송하기
			mailSender.send(mimeMessage);
			
			
//			int result = mapper.updateDelFl(email, memberDelFl);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0; // 예외 발생 == 실패 == 0 반환
		}
		
		return 1; // 예외 발생 X == 성공 == 1 반환
	}
	
	
	
	
	
	
	@Override
	public int normalMessage(String htmlName, String email) {
		try {
			
			String emailTitle = null; // 발송되는 이메일 제목
			String memberDelFl = "Y";
			
			// 이메일 발송 시 사용할 html 파일의 이름에 따라
			// 이메일 제목, 내용을 다르게 설정
			switch (htmlName) {
			
			case "manager-info":
				emailTitle = "탈퇴 취소 되어 정상 가입 되었습니다";
				break;
			}
			
			// MimeMessage : 메일 발송 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			// MimeMessageHelper :
			// Spring에서 제공하는 메일 발송 도우미
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setTo(email); // 받는 사람 이메일 세팅
			helper.setSubject(emailTitle); // 이메일 제목 세팅
			
			helper.setText(loadHtml(memberDelFl, htmlName), true); // 이메일 내용 세팅
			
//			helper.addInline("logo", new ClassPathResource("static/images/logo.png"));
			
			// 메일 발송하기
			mailSender.send(mimeMessage);
			
			
//			int result = mapper.updateDelFl(email, memberDelFl);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0; // 예외 발생 == 실패 == 0 반환
		}
		
		return 1; // 예외 발생 X == 성공 == 1 반환
	}
	
	
	
	
	
	
	
	// ---------------------------------------------------------
	
	
	// HTML 파일을 읽어와 String으로 변환 (타임리프 적용)
	public String loadHtml(String newPw, String htmlName) {

		// org.tyhmeleaf.Context 선택!!
		Context context = new Context();

		// 타임리프가 적용된 HTML에서 사용할 값 추가
		context.setVariable("newPw", newPw);

		// templates/email 폴더에서 htmlName과 같은
		// .html 파일 내용을 읽어와 String으로 변환
		return templateEngine.process("flea/email/" + htmlName, context);

	}

	private String createNewPw() {
		int length = 7;
		String randomPw = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder newPw = new StringBuilder(length);
		for (int i = 0; i < length - 1; i++) {
			newPw.append(randomPw.charAt(random.nextInt(randomPw.length())));
		}

		newPw.append("!");

		return newPw.toString();
	}
	
	
	

}
