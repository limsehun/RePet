package edu.kh.repet.email.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	

	public final RedisUtil redisUtil;
	
	public final EmailService service;
	
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
	
	
	// 인증 번호 발송
	@ResponseBody
	@PostMapping("sendNewPw")
	public int sendNewPw(@RequestBody String email) {
		
		return service.sendNewPw("login", email);
	}


//  @Autowired
//  private JavaMailSender mailSender;
//
//  @Autowired
//  private DataSource dataSource;
//
//  @PostMapping("/reset-password")
//  public String resetPassword(@RequestParam String email) {
//      String newPassword = generateRandomPassword();
//      
//      // 이메일로 새 비밀번호 전송
//      sendEmail(email, newPassword);
//      
//      // 데이터베이스에 비밀번호 업데이트
//      updatePasswordInDB(email, newPassword);
//      
//      return "비밀번호가 이메일로 전송되었습니다.";
//  }
//
//  private String generateRandomPassword() {
//      // 간단한 랜덤 비밀번호 생성 로직 (예: 8자리)
//      int length = 8;
//      String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//      Random random = new Random();
//      StringBuilder password = new StringBuilder();
//      
//      for (int i = 0; i < length; i++) {
//          password.append(chars.charAt(random.nextInt(chars.length())));
//      }
//      
//      return password.toString();
//  }
//
//  private void sendEmail(String email, String newPassword) {
//      SimpleMailMessage message = new SimpleMailMessage();
//      message.setTo(email);
//      message.setSubject("비밀번호 재설정");
//      message.setText("새 비밀번호: " + newPassword);
//      mailSender.send(message);
//  }
//
//  private void updatePasswordInDB(String email, String newPassword) {
//      String sql = "UPDATE users SET password = ? WHERE email = ?";
//      
//      try (Connection conn = dataSource.getConnection();
//           PreparedStatement pstmt = conn.prepareStatement(sql)) {
//           
//          pstmt.setString(1, newPassword);
//          pstmt.setString(2, email);
//          pstmt.executeUpdate();
//      } catch (SQLException e) {
//          e.printStackTrace();
//          // 예외 처리 로직 추가 가능
//      }
//  }
//	
//	
	
//	 int length = 7;
//   String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@";
//   Random random = new Random();
//   StringBuilder password = new StringBuilder(length);
//   for (int i = 0; i < length; i++) {
//       password.append(chars.charAt(random.nextInt(chars.length())));
//   }
//   return password.toString();
	
}