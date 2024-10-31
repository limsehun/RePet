package edu.kh.repet.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.board.dto.ReportBoard;

@Mapper
public interface ReportBoardMapper {

	// 게시글 신고
	int reportInsert(ReportBoard reportBoard);


}
