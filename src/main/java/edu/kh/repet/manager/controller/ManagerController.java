package edu.kh.repet.manager.controller;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.manager.service.ManagerService;
import edu.kh.repet.member.dto.Member;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("manager")
public class ManagerController {
	
	@Autowired
	private ManagerService service;
	


	@RequestMapping("manager-info") // "/" 요청 매핑(method 가리지 않음)
	public String managerPage(
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		if(loginMember.getMemberNo() != 50) return "redirect:/";
		
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
	
	
	
	
	@GetMapping("{:[0-9]+}")
	public String selectMemberList(
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		Model model,
		@RequestParam Map<String, Object> paramMap) {
		

		
		Map<String, Object> map = null;
		
		// 검색이 아닌 경우 == 일반 목록 조회
		if(paramMap.get("key") == null) {
			// 서비스 호출 후 결과 반환 받기
			// - 목록 조회인데 Map으로 반환 받는 이유?
			//  -> 서비스에서 여러 결과를 만들어 내야되는데
			//     메서드는 반환을 1개만 할 수 있기 때문에
			//     Map으로 묶어서 반환 받을 예정
			map = service.selectMemberList(cp);
		
		} else { // 검색한 경우
			// paramMap에 key, query 담겨 있음
			map = service.selectSearchList(cp, paramMap);
			
		}
		
		// map에 묶여있는 값 풀어놓기
//		List<Member> memberList = mapper.selectMemberList(bounds);
		List<Member> memberList = (List<Member>)map.get("memberList");
		Pagination pagination = (Pagination)map.get("pagination");
		
		// 정상 조회 되었는지 log 확인
		// for(Board b : boardList) log.debug(b.toString());
		// log.debug(pagination.toString());
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("pagination", pagination);
		
		return "manager/manager-info";
	}
	
	
	

}
