package edu.kh.repet.mypage.service;

import java.util.List;
import java.util.Map;

import edu.kh.repet.board.dto.Board;

public interface MyPageService {

	// 프로필 회원 조회
	Map<String, Object> memberList(int memberNo);

	Map<String, Object> selectLikeList(int memberNo, int cp);

}
