package edu.kh.repet.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.repet.board.dto.ReportBoard;
import edu.kh.repet.board.service.ReportBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("reportBoard")
public class ReportBoardController {

	private final ReportBoardService service;
	
	
	// 팝업 창 열기
	@GetMapping("reportPopup")
	public String reportPopup(
			@RequestParam("boardNo") int boardNo,
			Model model
			) {
		model.addAttribute("boardNo", boardNo);
		
		return "/board/reportPopup";
	}
	
	
//신고하기
  @ResponseBody
  @PostMapping("report")
  public ResponseEntity<?> reportInsert(@RequestBody ReportBoard reportBoard) {
      try {
          int result = service.reportInsert(reportBoard);
          if (result > 0) {
              return ResponseEntity.ok(result);
          } else {
              return ResponseEntity.badRequest().body("신고 접수에 실패했습니다.");
          }
      } catch (Exception e) {
          log.error("신고 중 오류 발생", e);
          return ResponseEntity.status(500).body("서버 오류 발생");
      }
  }
	
	
	
}
