package edu.kh.repet.manager.boardmanager.service;

import java.util.List;

import edu.kh.repet.board.dto.Board;

public interface BoardManagerService {

	// 보드 리스트 조회
	 List<Board> selectBoardList(int cp);


}
