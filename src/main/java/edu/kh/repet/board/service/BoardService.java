package edu.kh.repet.board.service;

import java.util.Map;

public interface BoardService {

	/** 게시글 목록 조회
	 * @param boardCode
	 * @param cpage
	 * @return map
	
	/** 게시글 목록 조회
	 * @param boardCode
	 * @param cpage
	 * @return
	 */
	Map<String, Object> selectBoardList(int boardCode, int cpage);

}
