package edu.kh.repet.manager.transaction.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.repet.board.dto.Board;

import edu.kh.repet.board.dto.ReportBoard;

import edu.kh.repet.member.dto.Member;

@Mapper
public interface TransactionMapper {

	// 중고 전체 조회
	int getListCount();

	List<Board> selectBoardList(RowBounds rowBounds);

	int getSearchCount(Map<String, Object> paramMap);

	List<Board> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);

	int deleteManegement(int boardNo);
	

	
	
	// 중고 신고 게시판
	int getSearchReportCount();

	List<ReportBoard> selectReportList(RowBounds rowBounds);

	int getSearchReportCount(Map<String, Object> paramMap);

	List<ReportBoard> selectSearchReportList(Map<String, Object> paramMap, RowBounds rowBounds);



	List<Board> selectReportedList(RowBounds rowBounds);

	List<Board> selectSearchReportedList(RowBounds rowBounds);



	List<Board> reportCount1(List<ReportBoard> boardList);

	List<Member> reportCount(List<ReportBoard> boardList);


	int deleteReport(int boardNo);

	int deleteReportBoard(int boardNo);





	
}