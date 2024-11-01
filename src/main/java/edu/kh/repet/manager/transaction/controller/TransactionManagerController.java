package edu.kh.repet.manager.transaction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("management")
	public String management() {
		return "manager/transaction/management";
	}
	
	@RequestMapping("report")
	public String report() {
		return "manager/transaction/report";
	}
	
	// 중고거래 게시판 관리
	@ResponseBody
	@GetMapping("selectManagement")
	public Map<String, Object> selectManagement(
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		@RequestParam Map<String, Object> paramMap) {
		
		
		Map<String, Object> map = null;
		
		log.debug("paramMap : {}", paramMap);
		// 검색이 아닌 경우 == 일반 목록 조회
		if(paramMap.get("key") == null) {
			map = service.selectBoardList(cp);
		} else { // 검색한 경우
			map = service.selectSearchList(cp, paramMap);
		}
		
		
		return map;
	}
	
	@ResponseBody
	@GetMapping("deleteManagement")
	public int deleteManagement(
		@RequestParam("boardNo") int boardNo){
		return service.deleteManagement(boardNo);
	}
	
	
	// 중고거래 신고 관리
	@ResponseBody
	@GetMapping("selectReport")
	public Map<String, Object> selectReport(
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> map = null;
		
		// 검색이 아닌 경우 == 일반 목록 조회
		if(paramMap.get("key") == null) {
			map = service.selectReportList(cp);
		} else {
			map = service.selectSearchReportList(cp, paramMap);
		}
		
		
		return map;
	}
	
	
	@ResponseBody
	@GetMapping("deleteReport")
	public int deleteReport(
		@RequestParam("boardNo") int boardNo){
		return service.deleteReport(boardNo);
	}
	
	@ResponseBody
	@GetMapping("deleteReportBoard")
	public int deleteReportBoard(
			@RequestParam("boardNo") int boardNo){
		return service.deleteReportBoard(boardNo);
	}
	
	
}
