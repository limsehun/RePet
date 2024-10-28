package edu.kh.repet.mypage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	
	// 좋아요 게시글 리스트 조회(비동기)
	// 페이지 네이션 객체에 게시물 수, 페이지 그룹 설정
	@ResponseBody
	@GetMapping("selectLikeList")
	public Map<String, Object> selectLikeList(
				@SessionAttribute("loginMember") Member loginMember,
				@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		return service.selectLikeList(loginMember.getMemberNo(), cp);
	}
	
	
	//회원 정보 수정
  @PostMapping("modify")
  public String updateMemberInfo(
  				@SessionAttribute("loginMember") Member loginMember,
  				@RequestParam("memberPw") String memberPw,
          @RequestParam("newPw") String newPw,
          @RequestParam("memberNickname") String memberNickname,
          RedirectAttributes ra
  		) {
  	
      // 서비스 호출하여 업데이트 실행
      int result = service.updateMemberInfo(memberPw, loginMember, newPw, memberNickname);

      // 성공 여부에 따라 메시지 설정
      String message = null;
  		String path = null;
  		
  		if(result > 0) {
  			message =  "수정 성공";
  			path = "info"; // 내 정보 페이지로 리다이렉트
  			
  			loginMember.setMemberNickname(memberNickname);
  		}else {
  			message =  "수정에 실패하였습니다.";
  			path = "info"; // 비밀번호 변경 페이지로 리다이렉트
  		}
  		
  		ra.addFlashAttribute("message", message);
  		
      // myPage/info 페이지로 리다이렉트
      return "redirect:" + path;
  }
  
  
  // 비밀번호 유효성 검사
  @ResponseBody
  @GetMapping("checkPw")
  public int checkPw(
  			@RequestParam("inputPw") String inputPw,
  			@SessionAttribute("loginMember") Member loginMember
  		) {
  	
  	return service.checkPw(inputPw, loginMember.getMemberNo());
  }
  
  
	
  // 닉네임 유효성 검사
  @ResponseBody
  @GetMapping("nicknameCheck")
  public int nicknameCheck(
  			@RequestParam("nickname") String nickname
  		) {
  	
  	return service.nicknameCheck(nickname);
  }
	
	
	

	@GetMapping("board")
	public String myBoard() {
		return "myPage/myPage-board";
	}

}
