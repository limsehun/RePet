package edu.kh.repet.board.service;

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

}
