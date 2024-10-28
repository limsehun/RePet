package edu.kh.repet.mypage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService service;

	// 좋아요 리스트 조회
	// 게시물 좋아요 수 조회
	@GetMapping("info")
	public String likeList(
				@SessionAttribute("loginMember") Member loginMember,
				Model model
			) {
		
		Map<String, Object> map = service.memberList(loginMember.getMemberNo());
		
		Member member = (Member)map.get("memberList");
		int likeCount = (int)map.get("likeCount");
		
		System.out.println(member);
		System.out.println(likeCount);
		
		model.addAttribute("member", member);
		model.addAttribute("likeCount", likeCount);

		return "myPage/myPage-info";
	}
	
	
	
	@ResponseBody
	@GetMapping("selectLikeList")
	public Map<String, Object> selectLikeList(
				@SessionAttribute("loginMember") Member loginMember,
				@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		return service.selectLikeList(loginMember.getMemberNo(), cp);
	}
	
	
	
	
	

	@GetMapping("board")
	public String myBoard() {
		return "myPage/myPage-board";
	}

}
