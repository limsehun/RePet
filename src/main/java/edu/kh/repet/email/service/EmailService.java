package edu.kh.repet.email.service;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kh.repet.email.mapper.EmailMapper;

public interface EmailService {
	
	
	int sendNewPw(String htmlName, String email);

	int secessionMessage(String htmlName, String email);

	int normalMessage(String html, String email);

	

}
