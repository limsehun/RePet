package edu.kh.repet.mypage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.member.dto.Member;
import edu.kh.repet.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService service;

	// 좋아요 리스트 조회
	@GetMapping("info")
	public String likeList(
				@RequestParam("memberNo") int memberNo,
				Model model
			) {

		Member member = service.memberList(memberNo);
		
		model.addAttribute("member", member);

		return "myPage/myPage-info";
	}
	
	
	@ResponseBody
	@GetMapping("selectLikeList")
	public List<Board> selectLikeList(
				@RequestParam("memberNo") int memberNo
			) {
		
		return service.selectLikeList(memberNo);
	}
	
	
	
	
	

	@GetMapping("board")
	public String myBoard() {
		return "myPage/myPage-board";
	}

}