package edu.kh.repet.member.service;

import edu.kh.repet.member.dto.Member;

public interface MemberService {

	Member login(String memberEmail, String memberPw);
	
	int signUp(Member inputMember);

	int emailCheck(String memberEmail);
	
	int pwCheck(String memberPw);
	
	int nicknameCheck(String memberNickname);





}
