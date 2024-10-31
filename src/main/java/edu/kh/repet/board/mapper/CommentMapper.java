package edu.kh.repet.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.repet.board.dto.Comment;

@Mapper
public interface CommentMapper {

	// 댓글 등록
	int insertComment(Comment comment);

	
	// 댓글 목록 조회
	List<Comment> selectComments(int boardNo);


	// 댓글 삭제
	int deleteComment(@Param("commentNo") int commentNo, @Param("memberNo")  int memberNo);



	// 댓글 수정
	int updateComment(Comment comment);

}
