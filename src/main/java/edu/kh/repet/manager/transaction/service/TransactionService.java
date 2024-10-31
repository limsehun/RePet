package edu.kh.repet.manager.
transaction.service;

import java.util.Map;

public interface TransactionService {

	// 중고게시판
	Map<String, Object> selectBoardList(int cp);

	Map<String, Object> selectSearchList(int cp, Map<String, Object> paramMap);

	int deleteManagement(int boardNo);

	// 중고 신고게시판
	Map<String, Object> selectReportList(int cp);

	Map<String, Object> selectSearchReportList(int cp, Map<String, Object> paramMap);


}


