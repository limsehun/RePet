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
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		Model model) throws IOException{
				
		urlStr = openApiUrl +
			"?serviceKey=" + serviceKey
			+"&_type=" + dataType
			+"&numOfRows=24";
		
		return service.selectAdoptList(urlStr, cp);	
	}
	
}
	

	