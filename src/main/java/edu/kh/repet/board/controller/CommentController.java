package edu.kh.repet.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.repet.board.dto.Comment;
import edu.kh.repet.board.service.CommentService;
import edu.kh.repet.member.dto.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {
	
	private final CommentService service;
	
	@ResponseBody
	@PostMapping("add")
	public ResponseEntity<Integer> addComment(
			@RequestBody Comment comment,
			@SessionAttribute("loginMember") Member loginMember
			){
		
		// 로그인한 사용자의 정보를 댓글 객체에 설정
		comment.setMemberNo(loginMember.getMemberNo());
		comment.setMemberNickname(loginMember.getMemberNickname());
		
		int result = service.addComment(comment);
		
		return ResponseEntity.ok(result);
	}
	
	
	// 댓글 목록 조회
	@ResponseBody
	@GetMapping("list/{boardNo}")
	public ResponseEntity<List<Comment>> getComments(
			@PathVariable("boardNo") int boardNo
			){
		
		List<Comment> comments = service.getComments(boardNo);
		return ResponseEntity.ok(comments);
	}

















}
