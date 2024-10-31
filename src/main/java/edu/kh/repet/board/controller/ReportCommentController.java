package edu.kh.repet.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.repet.board.dto.ReportComment;
import edu.kh.repet.board.service.ReportCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("reportComment")
public class ReportCommentController {
	
	private final ReportCommentService service;
	
	@GetMapping("reportPopup")
	public String commentReportPopup(
			@RequestParam("commentNo") int commentNo,
			Model model
			) {
		model.addAttribute("commentNo", commentNo);
		return "/comment/commentReportPopup";
	}


	 @PostMapping("/report")
   public ResponseEntity<Integer> reportComment(@RequestBody ReportComment reportComment) {
       int result = service.reportComment(reportComment);
       return ResponseEntity.ok(result);
   }










}
