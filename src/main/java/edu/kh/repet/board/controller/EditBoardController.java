package edu.kh.repet.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EditBoardController {

	
	@GetMapping("board/boardWrite")
	public String boardInsert(
			) {
		
		return "board/boardWrite";
	}
}
