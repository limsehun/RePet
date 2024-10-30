package edu.kh.repet.manager.boardmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("boardManager")
public class BoardManagerController {
	
	
	@GetMapping("boardManagement") // "/" 요청 매핑(method 가리지 않음)
	public String managerPage() {
		return "/boardManager/boardManagement";
	}
	
	
	
	
	
	

}
