package edu.kh.repet.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.repet.board.dto.Comment;
import edu.kh.repet.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper mapper;
	

	// 댓글 등록
	@Override
	public int addComment(Comment comment) {
		return mapper.insertComment(comment);
	}

	
	// 댓글 목록 조회
	@Override
	public List<Comment> getComments(int boardNo) {
		return mapper.selectComments(boardNo);
	}
	
	// 댓글 삭제
	@Override
	public int deleteComment(int commentNo, int memberNo) {
		return mapper.deleteComment(commentNo, memberNo);
	}
	
	@Override
	public Comment getCommentById(int commentNo) {
		return mapper.selectCommentById(commentNo);
	}

	@Override
	public int updateComment(Comment updatedComment) {
		return mapper.updateComment(updatedComment);
	}











}
