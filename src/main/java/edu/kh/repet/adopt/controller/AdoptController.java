package edu.kh.repet.adopt.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.repet.adopt.service.AdoptService;




@Controller
@Slf4j
@PropertySource("classpath:/config.properties")
@RequestMapping("adopt")
public class AdoptController{
	
	@Autowired 
	public AdoptService service;
	
	@Value("${adopt.serviceKey}")
	public String serviceKey; 
	
	@Value("${adopt.openApiUrl}")
	public String openApiUrl; 

	@Value("${adopt.dataType}")
	public String dataType; 
	
	
	String urlStr = null;
	
	@RequestMapping("main")
	public String requestMethodName() {
		return "adopt/main";
	}
	
	
	
	@ResponseBody
	@GetMapping("selectAdoptList")
	public Map<String, Object> selectAdoptList(
	    Map<String, Object> map,
	    @RequestParam(value = "page", defaultValue = "1") Integer page,
	    @RequestParam(value = "upkind", required = false) String upkind) throws IOException {

	    // 동물 종류 코드 설정
	    switch (upkind) {
	        case "dog":
	            upkind = "417000";
	            break;
	        case "cat":
	            upkind = "422400";
	            break;
	        case "others":
	            upkind = "429900";
	            break;
	        default:
	            upkind = ""; // 기본값 설정
	    }

	    // API 호출 URL 생성
	    String urlStr = openApiUrl +
	        "?serviceKey=" + serviceKey +
	        "&_type=" + dataType +
	        "&numOfRows=8" +
	        "&pageNo=" + page +
	        (upkind.isEmpty() ? "" : "&upkind=" + upkind); // upkind가 비어있을 경우 추가하지 않음

	    log.info("------------------------------------------------------ " + urlStr);

	    // 서비스 호출 및 결과 반환
	    return service.selectAdoptList(urlStr);
	}

	
	
	
	
}
	




	