package edu.kh.repet.hospital.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {

    // 네이버 지도 API 키
    @Value("${naver.api.key}")
    private String naverMapApiKey;
    @Value("${naver.secret}")
    private String naverSecret;
    
   // 네이버 검색 API 키 
    @Value("${naver.client.id}")
    private String naverClientId;
    @Value("${naver.client.secret}")
    private String naverClientSecret;

    // 경기도 및 서울 공공 API 키
    @Value("${gg.api.key}")
    private String gyeonggiApiKey;
    @Value("${seoul.api.key}")
    private String seoulApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    

    

    // 네이버 검색 API 엔드포인트
    @GetMapping("/search")
    public ResponseEntity<?> searchNaver(@RequestParam("query") String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String url = "https://openapi.naver.com/v1/search/local.json?query=" + encodedQuery;

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", naverClientId);
            headers.set("X-Naver-Client-Secret", naverClientSecret);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // JSON 문자열을 Map으로 변환
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonResponse = mapper.readValue(response.getBody(), Map.class);

            // 필터링하여 필요한 데이터만 추출
            List<Map<String, Object>> items = (List<Map<String, Object>>) jsonResponse.get("items");
            if (items == null) {
	            	System.err.println("네이버 API 응답에 items가 없습니다.");
	            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("검색 결과가 없습니다.");
            	}
            List<Map<String, Object>> filteredResults = new ArrayList<>();

            for (Map<String, Object> item : items) {
                Map<String, Object> filteredItem = Map.of(
                    "title", item.get("title"),
                    "link", item.get("link"),
                    "address", item.get("address")
                );
                filteredResults.add(filteredItem);
            }

            // 필터링된 결과를 포함하여 응답 반환
            return ResponseEntity.ok(filteredResults);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("네이버 API 호출 중 오류 발생");
        }
    }
    
 // 네이버 지도 API 키 제공
    @GetMapping("/naver-map-key")
    public ResponseEntity<String> getNaverMapKey() {
        return ResponseEntity.ok(naverMapApiKey);
    }

 // 경기도 동물병원 데이터 제공 (페이지 인덱스를 매개변수로 받음)
    @GetMapping("/gyeonggi-hospitals")
    public ResponseEntity<String> getGyeonggiHospitals(@RequestParam("page") int pageIndex) {
        String url = "https://openapi.gg.go.kr/Animalhosptl?KEY=" + gyeonggiApiKey + "&Type=json&pIndex=" + pageIndex + "&pSize=1000";
        System.out.println("경기 병원 데이터 요청 URL: " + url); // URL 출력 로그
        
        try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("경기 병원 데이터 응답: " + response); // 응답 데이터 로그
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("경기도 API 호출 중 오류 발생");
        }
    }

    // 서울 동물병원 데이터 제공
    @GetMapping("/seoul-hospitals")
    public ResponseEntity<String> getSeoulHospitals(@RequestParam("page") int pageIndex) {
        // 서울 공공 데이터 API URL 설정
        int startIndex = (pageIndex - 1) * 1000 + 1;  // 예: 페이지 1일 때 시작 인덱스 1, 페이지 2일 때 시작 인덱스 1001
        int endIndex = pageIndex * 1000;               // 예: 페이지 1일 때 종료 인덱스 1000, 페이지 2일 때 종료 인덱스 2000

        String url = "http://openapi.seoul.go.kr:8088/" + seoulApiKey + "/json/LOCALDATA_020301/"
                     + startIndex + "/" + endIndex;
        
        System.out.println("서울 병원 데이터 요청 URL: " + url); // URL 출력 로그

        try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("서울 병원 데이터 응답: " + response); // 응답 데이터 로그
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서울 API 호출 중 오류 발생");
        }
    }
}