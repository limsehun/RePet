package edu.kh.repet.board.service;

import java.util.List;

import edu.kh.repet.board.dto.Comment;

public interface CommentService {

	// 댓글 등록
	int addComment(Comment comment);

	
	// 댓글 목록 조회
	List<Comment> getComments(int boardNo);

}
