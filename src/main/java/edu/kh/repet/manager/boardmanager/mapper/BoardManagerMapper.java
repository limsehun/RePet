package edu.kh.repet.manager.boardmanager.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.ReportBoard;

@Mapper
public interface BoardManagerMapper {

	// 게시물 수
	int boardCount();

	// 게시물 전체 조회

	// 게시물 삭제
	int deleteBoard(int boardNo);

	List<Board> selectBoardList(RowBounds rowBounds);


	// 신고 누적 수
	int reportCount();

	// 신고 게시물 조회
	List<ReportBoard> reportBoardList(RowBounds rowBounds);

	int getSearchCount(Map<String, Object> paramMap);

	List<Board> searchBoardList(Map<String, Object> paramMap, RowBounds rowBounds);



}
