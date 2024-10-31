package edu.kh.repet.board.service;

import java.util.List;
import java.util.Map;

import edu.kh.repet.board.dto.Board;

public interface BoardService {

	
	
	/** 게시글 목록 조회
	 * @param boardCode
	 * @param cpage
	 * @return
	 */
	Map<String, Object> selectBoardList(int boardCode, int cpage);

	Board boardDetail(Map<String, Integer> map);

	/** 조회수 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 좋아요
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	Map<String, Object> boardLike(int boardNo, int memberNo);

	// 게시글 목록으로 이동
	int getCurrentPage(Map<String, Object> paramMap);

	
	// 조회수가 높은 게시글 5개 조회
	List<Board> getTop5Boards();

	// 검색 결과 조회

	Map<String, Object> searchBoard(int boardCode, int cpage, Map<String, Object> paramMap);

}
