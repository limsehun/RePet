package edu.kh.repet.mypage.service;

import java.util.Map;


public interface MyPageService {

	// 프로필 회원 조회
	Map<String, Object> memberList(int memberNo);

	Map<String, Object> selectLikeList(int memberNo, int cp);

}
