package edu.kh.repet.manager.boardmanager.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.manager.boardmanager.service.BoardManagerService;
import lombok.RequiredArgsConstructor;

@Controller // 이 어노테이션을 추가
@RequestMapping("manager/boardManager")
@RequiredArgsConstructor
public class BoardManagerController {
	
	private final BoardManagerService service;
	
	@GetMapping("boardManagement")
	public String managerPage() {
	    return "manager/boardManager/boardManagement";
	}
	
	
	// 보드 리스트 조회
	@ResponseBody
	@GetMapping("selectBoardList")
	public List<Board> selectBoardList(
				@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		return service.selectBoardList(cp);
	}
	
}
	
	
	
	
	

