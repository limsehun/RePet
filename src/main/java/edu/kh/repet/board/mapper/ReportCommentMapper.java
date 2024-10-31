package edu.kh.repet.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.board.dto.ReportComment;

@Mapper
public interface ReportCommentMapper {

	
	int insertReportComment(ReportComment reportComment);

}
