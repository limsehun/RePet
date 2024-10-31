package edu.kh.repet.manager.boardmanager.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.repet.board.dto.Board;

@Mapper
public interface BoardManagerMapper {

	// 게시물 수
	int boardCount();

	// 게시물 전체 조회
	List<Board> selectBoardList(RowBounds rowBounds);

	// 게시물 삭제
	int deleteBoard(int boardNo);


}
