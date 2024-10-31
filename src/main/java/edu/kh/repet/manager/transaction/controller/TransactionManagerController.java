package edu.kh.repet.manager.transaction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.manager.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("manager/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionManagerController {
	
	public final TransactionService service;
	
	// 중고거래 게시판 관리
	
	@GetMapping("management")
	public String management(
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		Model model,
		@RequestParam Map<String, Object> paramMap) {
		
		
		Map<String, Object> map = null;
		
		log.debug("paramMap : {}", paramMap);
		// 검색이 아닌 경우 == 일반 목록 조회
		if(paramMap.get("key") == null) {
			map = service.selectBoardList(cp);
		} else { // 검색한 경우
			map = service.selectSearchList(cp, paramMap);
			
		}
		
		// map에 묶여있는 값 풀어놓기
		List<Board> boardList = (List<Board>)map.get("boardList");
		Pagination pagination = (Pagination)map.get("pagination");
		
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagination", pagination);
		
		return "manager/transaction/management";
	}
	
	
	
	
	// 중고거래 신고 관리
	
	@GetMapping("report")
	public String report(
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		Model model,
		@RequestParam Map<String, Object> paramMap) {
		

		Map<String, Object> map = null;
		
		// 검색이 아닌 경우 == 일반 목록 조회
		if(paramMap.get("query") == null) {
			map = service.selectReportList(cp);
		} else {
			map = service.selectSearchReportList(cp, paramMap);
			
		}
		
		// map에 묶여있는 값 풀어놓기
		List<Board> boardList = (List<Board>)map.get("boardList");
		Pagination pagination = (Pagination)map.get("pagination");
		
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagination", pagination);
		
		return "manager/transaction/report";
	}
	
}
