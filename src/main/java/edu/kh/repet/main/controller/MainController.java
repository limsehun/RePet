package edu.kh.repet.main.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.bind.annotation.GetMapping;

import edu.kh.repet.adopt.service.AdoptService;
import edu.kh.repet.main.service.MainService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({ "loginMember" })
@Controller // 요청 / 응답 제어 하는 역할 명시 + Bean 등록
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MainController {
	
	@Value("${adopt.serviceKey}")
	public String serviceKey; 
	
	@Value("${adopt.openApiUrl}")
	public String openApiUrl; 

	@Value("${adopt.dataType}")
	public String dataType; 
	
	public final AdoptService adoptService;

	private final MainService service;

	@RequestMapping("/") // "/" 요청 매핑(method 가리지 않음)
	public String mainPage() {
		return "common/main";
	}

	@ResponseBody
	public String login(
			@RequestBody String username, 
			@RequestBody String password) {
		
		return "common/main";
	}

	@ResponseBody
	@GetMapping("main/selectList")
	public Map<String, Object> selectAdoptList(
	    Map<String, Object> map) throws IOException {

	    // API 호출 URL 생성
	    String urlStr = openApiUrl +
	        "?serviceKey=" + serviceKey +
	        "&_type=" + dataType +
	        "&numOfRows=4";


	    // 서비스 호출 및 결과 반환
	    return adoptService.selectAdoptList(urlStr);
	}

}
