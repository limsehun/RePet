package edu.kh.repet.board.controller;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;
import edu.kh.repet.board.service.BoardService;
import edu.kh.repet.board.service.EditBoardService;
import edu.kh.repet.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("editBoard")
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class EditBoardController {
	
	private final EditBoardService service;
	
	private final BoardService boardService;
	
	
	
	@GetMapping("{boardCode}/insert")
	public String boardInsert(
			) {
		
		return "board/boardWrite";
	}
	
	
	 // 게시글 저장
  @PostMapping("insert")
  @ResponseBody
  public int savePost(
  			@SessionAttribute("loginMember") Member loginMember,
  			@ModelAttribute Board board
  		) throws IOException {
  	
  	
//          Board board = new Board();
//          
//          board.setBoardTitle(boardTitle);
//          
//          board.setBoardContent(boardContent);
  	
			  	 // 로그인한 사용자의 memberNo와 닉네임 설정
			    board.setMemberNo(loginMember.getMemberNo());
			    board.setMemberNickname(loginMember.getMemberNickname()); // 닉네임 설정
          
          int result = service.savePost(board);
          
          if(result > 0) {
          	return result;
          }
          
          return result;
          
      
  }
  
  // 이미지 업로드
  @ResponseBody
  @PostMapping("uploadImage")
  public String uploadImage(@RequestParam("file") MultipartFile file) {
  	
  	
  	
  	return service.uploadImage(file);
  }
  
  
  // 게시물 삭제
  @PostMapping("delete")
  public String deleteBoard(
          @RequestParam("boardNo") int boardNo,
          @SessionAttribute("loginMember") Member loginMember,
          RedirectAttributes ra,
          @RequestHeader("referer") String referer
  		) {

      int memberNo = loginMember.getMemberNo();

      // 게시글 작성자와 로그인한 사용자가 같은지 확인하고 삭제 진행
      int result= service.deleteBoard(boardNo, memberNo);

      String message = null;
      String path   = null;
      
      if(result > 0) { // 성공
  			message = "삭제 되었습니다";
  			// path = "삭제된 게시글이 존재하던 게시판 목록 주소";
  			
  			String regExp = "/board/[0-9]+";
  			
  			Pattern pattern = Pattern.compile(regExp);
  			Matcher matcher = pattern.matcher(referer);
  			
  			if(matcher.find()) { // 일치하는 부분을 찾은 경우
  				path = matcher.group(); // /board/1
  			}
  			
  		} else { // 실패
  			message = "삭제 실패";
  			path = referer; // 삭제를 요청했던 상세 조회 페이지 주소
  		}
  		
  		ra.addFlashAttribute("message", message);
  		
  		 
  		return "redirect:" + path;
  	}
  
  
  // 게시글 수정 화면 전환
  @PostMapping("{boardCode}/{boardNo}/boardModifyView")
  public String boardModifyView(
  		@PathVariable("boardCode") int boardCode,
  		@PathVariable("boardNo") int boardNo,
  		@SessionAttribute("loginMember") Member loginmember,
  		RedirectAttributes ra,
  		Model model
  		) {
  	
  	Map<String, Integer> map = Map.of("boardCode", boardCode, "boardNo", boardNo);
  	Board board = boardService.boardDetail(map);
  	
  	// 게시글이 존재하지 않는경우
  	if(board == null) {
  		ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다");
  		return "redirect:/board/" + boardCode;
  	}
  	
  	// 게시글 작성자가 로그인한 회원이 아닌경우
  	if(board.getMemberNo() != loginmember.getMemberNo()) {
  		ra.addFlashAttribute("message", "해당 글 작성자만 수정이 가능합니다");
  		return "redirect:/board/" + boardCode;
  	}
  	
  	
  	model.addAttribute("board", board);
  	return "board/boardModify";
  }
  
//게시글 수정 요청 처리
  @PostMapping("{boardCode}/{boardNo}/update")
  @ResponseBody
  public int updateBoard(
          @PathVariable("boardCode") int boardCode,
          @PathVariable("boardNo") int boardNo,
          @SessionAttribute("loginMember") Member loginMember,
          @RequestParam("boardTitle") String boardTitle,
          @RequestParam("boardContent") String boardContent) {

      // 수정할 게시글 번호, 회원 번호, 게시판 코드 설정
      Board board = new Board();
      board.setBoardNo(boardNo);
      board.setMemberNo(loginMember.getMemberNo());
      board.setBoardCode(boardCode);
      board.setBoardTitle(boardTitle);
      board.setBoardContent(boardContent);

      // 게시글 수정 처리
      return service.updateBoard(board);
  }
  
  
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}