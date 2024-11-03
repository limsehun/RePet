package edu.kh.repet.mypage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.mypage.service.MyPageService;
import jakarta.servlet.http.HttpSession;
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
		
		
    if (loginMember == null) {
      return "/"; // 로그인 페이지로 리다이렉트
    }
		
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
  				@RequestParam("profileImg") MultipartFile profileImg,
  				@SessionAttribute("loginMember") Member loginMember,
  				@RequestParam("memberPw") String memberPw,
          @RequestParam("newPw") String newPw,
          @RequestParam("memberNickname") String memberNickname,
          HttpSession session,
          RedirectAttributes ra
  		) {
  	
      // 서비스 호출하여 업데이트 실행
      int result = service.updateMemberInfo(memberPw, loginMember, newPw, memberNickname, profileImg);

      // 성공 여부에 따라 메시지 설정
      String message = null;
  		String path = null;
  		
  		if(result > 0) {
  			
  			message =  "수정 성공";
  			path = "info"; // 내 정보 페이지로 리다이렉트
  			
  			// 세션의 loginMember 객체에 새로운 닉네임 반영
        loginMember.setMemberNickname(memberNickname);
        
        // 세션에 수정된 정보를 다시 저장
        session.setAttribute("loginMember", loginMember);
        
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
  
  
  // 회원 탈퇴
  @ResponseBody
  @PutMapping("delete")
  public int deleteUser(
  			@SessionAttribute("loginMember") Member loginMember,
  			SessionStatus status
  		) {
  	
  	 int result = service.deletUser(loginMember.getMemberNo());
     
     if (result > 0) { // 탈퇴 성공 시
    	 status.setComplete(); // 세션 만료 -> 로그아웃
     }
  	
		
    return result;
  }
  
  
  
  
  
  // 회원이 작성한 게시물 리스트
	@ResponseBody
	@GetMapping("selectBoardList")
	public Map<String, Object> selectBoardList(
				@SessionAttribute("loginMember") Member loginMember,
				@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		return service.selectBoardList(loginMember.getMemberNo(), cp);
	}
	
	
	
  
  
  @GetMapping("/board")
  public String myPageBoard(
  			@SessionAttribute("loginMember")
  			Member loginMember, Model model
  		) {
  	
  	
  	int boardCount = service.boardCount(loginMember.getMemberNo());
  	
  	int commentCount = service.commentCount(loginMember.getMemberNo());
  	
  	
  	model.addAttribute("boardCount", boardCount);
  	model.addAttribute("commentCount", commentCount);
  	
    if (loginMember != null) {
        model.addAttribute("member", loginMember);
    }
    
    
    return "myPage/myPage-board";
  }
  
  
  
 	@ResponseBody
 	@GetMapping("selectCommentList")
 	public Map<String, Object> selectCommentList(
 				@SessionAttribute("loginMember") Member loginMember,
 				@RequestParam(value="cp", required = false, defaultValue = "1") int cp
 			) {
 		
 		// 페이지 네이션 수정
 		
 		return service.selectCommentList(loginMember.getMemberNo(), cp);
 	}
  

  
  
  
  
	
	
	


}
