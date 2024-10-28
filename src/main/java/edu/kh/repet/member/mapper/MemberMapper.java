package edu.kh.repet.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.member.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(String memberEmail);

	int signUp(Member inputMember);

	int emailCheck(String memberEmail);

	int pwCheck(String memberPw);
	
	int nicknameCheck(String memberNickname);



}
