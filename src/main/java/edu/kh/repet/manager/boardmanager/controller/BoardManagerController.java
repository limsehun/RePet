package edu.kh.repet.manager.boardmanager.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.repet.manager.boardmanager.service.BoardManagerService;
import lombok.RequiredArgsConstructor;

@Controller // 이 어노테이션을 추가
@RequestMapping("manager/board")
@RequiredArgsConstructor
public class BoardManagerController {
	
	private final BoardManagerService service;
	
	@GetMapping("boardManagement")
	public String managerPage() {
	    return "manager/board/boardManagement";
	}
	
	
	// 게시물 리스트 조회
	@ResponseBody
	@GetMapping("selectBoardList")
	public Map<String, Object> selectBoardList(
				@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		return service.selectBoardList(cp);
	}
	
	
	// 신고 게시물 리스트 조회
	@ResponseBody
	@GetMapping("reportBoardList")
	public Map<String, Object> reportBoardList(
				@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		return service.reportBoardList(cp);
	}
	
	
	
	// 게시물 삭제
	@ResponseBody
	@PostMapping("delete")
	public int deleteBoard (
				@RequestBody int currentBoardNo
			) {
	
		int result = service.deleteBoard(currentBoardNo);
		
		   
		return result;
	}

	
	@GetMapping("boardReport")
	public String boardReport() {
		
	    return "manager/board/boardReport";
	}
	
}
	
	
	
	
	

