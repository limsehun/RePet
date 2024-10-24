package edu.kh.repet.email.service;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kh.repet.email.mapper.EmailMapper;

public class EmailService {
	
	
	/** 이메일 발송 서비스
	 * @param string
	 * @param email
	 * @return
	 */
	int sendEmail(String htmlName, String email);


}
