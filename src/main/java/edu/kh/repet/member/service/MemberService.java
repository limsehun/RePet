package edu.kh.repet.member.service;

import edu.kh.repet.member.dto.Member;

public interface MemberService {

	Member login(String email, String password);
	
	int signUp(Member inputMember);

	int emailCheck(String email);

	int nicknameCheck(String nickname);




}
