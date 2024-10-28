package edu.kh.repet.board.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.service.EditBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("editBoard")
@RequiredArgsConstructor
public class EditBoardController {

	private final EditBoardService service;
	
	@GetMapping("{boardCode}/insert")
	public String boardInsert(
			) {
		
		return "board/boardWrite";
	}
	
	 // 게시글 저장
  @PostMapping("insert")
  @ResponseBody
  public int savePost(
  				@ModelAttribute Board board
  		) {
  	
  	
//          Board board = new Board();
//          
//          board.setBoardTitle(boardTitle);
//          
//          board.setBoardContent(boardContent);
          
          int result = service.savePost(board);
          
          if(result > 0) {
          	return result;
          }
          
          return result;
          
      
  }
  
}