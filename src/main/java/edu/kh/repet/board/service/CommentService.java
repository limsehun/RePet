package edu.kh.repet.board.service;

import edu.kh.repet.board.dto.Comment;

public interface CommentService {

	// 댓글 등록
	int addComment(Comment comment);

}
