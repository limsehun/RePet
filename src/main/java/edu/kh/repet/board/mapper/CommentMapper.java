package edu.kh.repet.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.board.dto.Comment;

@Mapper
public interface CommentMapper {

	// 댓글 등록
	int insertComment(Comment comment);

	
	// 댓글 목록 조회
	List<Comment> selectComments(int boardNo);

}
