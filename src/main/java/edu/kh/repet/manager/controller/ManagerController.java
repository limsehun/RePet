package edu.kh.repet.manager.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.repet.manager.service.ManagerService;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("manager")
public class ManagerController {
	
	@Autowired
	private ManagerService service;

	@RequestMapping("manager-info") // "/" 요청 매핑(method 가리지 않음)
	public String managerPage() {
		return "manager/manager-info";
	}
	
	@ResponseBody
	@GetMapping("selectMemberList")
	public Map<String, Object> selectMemberList(
			@RequestParam("cp") int cp){
		return service.selectMemberList(cp);
	}
	
	@ResponseBody
	@PutMapping("changeStatus")
	public int changeStatus(@RequestBody int memberNo) {
		int result = service.changeStatus(memberNo);
		
		return result;
	}
	
	
	
	

}
