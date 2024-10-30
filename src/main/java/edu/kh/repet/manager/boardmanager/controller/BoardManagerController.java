package edu.kh.repet.manager.boardmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 이 어노테이션을 추가
@RequestMapping("manager/boardManager")
public class BoardManagerController {
	
	@GetMapping("boardManagement")
	public String managerPage() {
	    return "manager/boardManager/boardManagement";
	}
	
}
	
	
	
	
	

