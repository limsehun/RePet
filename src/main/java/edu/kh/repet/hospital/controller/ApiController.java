package edu.kh.repet.hospital.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	 @Value("${naver.api.key}") // config.properties에 저장된 네이버 맵 API 키
   private String naverMapApiKey;
	 
	 @Value("${naver.api.key}")
	 private String naverClientId;
	 
	 @Value("${naver.secret}")
	 private String naverClientSecret;

   @GetMapping("/api/naver-map-key")
   public String getNaverMapKey() {
       return naverMapApiKey;
   
   }
   

   @GetMapping("/api/naver-search-key")
   public ResponseEntity<Map<String, String>> getNaverSearchKey() {
       Map<String, String> keys = new HashMap<>();
       keys.put("clientId", naverClientId);
       keys.put("clientSecret", naverClientSecret);
       return ResponseEntity.ok(keys);
   }
}

