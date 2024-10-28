package edu.kh.repet.mypage.service;

import java.util.Map;

import edu.kh.repet.member.dto.Member;


public interface MyPageService {

	// 프로필 회원 조회
	Map<String, Object> memberList(int memberNo);

	
	Map<String, Object> selectLikeList(int memberNo, int cp);

	// 회원 수정
	int updateMemberInfo(String memberPw, Member loginMember, String newPw, String memberNickname);

}
