package edu.kh.repet.board.controller;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;
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
  		) throws IOException {
  	
  	
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
  
  @ResponseBody
  @PostMapping("uploadImage")
  public String uploadImage(@RequestParam("file") MultipartFile file) {
  	
  	
  	
  	return service.uploadImage(file);
  }
  
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}