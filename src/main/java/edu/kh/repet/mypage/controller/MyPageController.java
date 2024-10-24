package edu.kh.repet.mypage.controller;

import java.util.List;
import java.util.Map;

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
	// 게시물 좋아요 수 조회
	@GetMapping("info")
	public String likeList(
				@RequestParam("memberNo") int memberNo,
				Model model
			) {
		
		Map<String, Object> map = service.memberList(memberNo);
		
		Member member = (Member)map.get("memberList");
		int likeCount = (int)map.get("likeCount");
		
		model.addAttribute("member", member);
		model.addAttribute("likeCount", likeCount);

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
