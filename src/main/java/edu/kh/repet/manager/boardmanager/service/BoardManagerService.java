package edu.kh.repet.manager.boardmanager.service;

import java.util.Map;



public interface BoardManagerService {

	// 보드 리스트 조회
	Map<String, Object> selectBoardList(int cp);

	int deleteBoard(int boardNo);

	// 신고 게시물 리스트
	Map<String, Object> reportBoardList(int cp);



}
