package edu.kh.repet.hospital.controller;

//NaverApiController.java (새로 추가)
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class NaverApiController {

 @Value("${naver.client.id}")
 private String naverClientId;

 @Value("${naver.client.secret}")
 private String naverClientSecret;

 private final RestTemplate restTemplate = new RestTemplate();

 @GetMapping("/api/search")
 public ResponseEntity<String> searchNaver(@RequestParam("query") String query) {
     String url = "https://openapi.naver.com/v1/search/local.json?query=" + query;

     HttpHeaders headers = new HttpHeaders();
     headers.set("X-Naver-Client-Id", naverClientId);
     headers.set("X-Naver-Client-Secret", naverClientSecret);

     HttpEntity<String> entity = new HttpEntity<>(headers);
     ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
     return response;
 }
}
