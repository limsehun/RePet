package edu.kh.repet.adopt.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.kh.repet.adopt.dto.Adopt;
import edu.kh.repet.adopt.run.AdoptApp;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@PropertySource("classpath:/config.properties")
public class AdoptController{
	
	@Value("${adopt.serviceKey}")
	public String serviceKey; 
	
	@Value("${adopt.openApiUrl}")
	public String openApiUrl; 

	@Value("${adopt.dataType}")
	public String dataType; 
	
	
	String urlStr = null;
	
	@RequestMapping("adopt/main")
	public String callOpenApi(Model model) throws IOException{
				
		urlStr = openApiUrl +
			"?serviceKey=" + serviceKey
			+"&_type=" + dataType
			+"&numOfRows=8";

		// URL 객체 생성
        URL apiUrl = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();

        // 요청 메서드 설정
        urlConnection.setRequestMethod("GET");
        
        // 응답 코드 확인
        int responseCode = urlConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답일 경우
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            // 응답 읽기
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            
    		JsonObject totalObj = JsonParser.parseString(response.toString()).getAsJsonObject();		
    		JsonObject responseObj = totalObj.getAsJsonObject("response");
    		JsonObject bodyObj = responseObj.getAsJsonObject("body").getAsJsonObject("items");
    		JsonArray itemArr = bodyObj.getAsJsonArray("item");

    		ArrayList<Adopt> adoptList = new ArrayList<>();
    		for(int i=0; i<itemArr.size();i++) {
    			JsonObject item = itemArr.get(i).getAsJsonObject();
    			Adopt adopt = new Adopt();
    			adopt.setPopfile(item.get("popfile").getAsString());
    			adopt.setAge(item.get("age").getAsString());
    			adopt.setNeuterYn(item.get("neuterYn").getAsString());
    			adopt.setSexCd(item.get("sexCd").getAsString());
    			adopt.setCareNm(item.get("careNm").getAsString());
    			adopt.setCareTel(item.get("careTel").getAsString());
    			adopt.setCareAddr(item.get("careAddr").getAsString());
    			adopt.setProcessState(item.get("processState").getAsString());
    			adopt.setNoticeNo(item.get("noticeNo").getAsString());
    			adopt.setNoticeSdt(item.get("noticeSdt").getAsString());
    			adopt.setNoticeEdt(item.get("noticeEdt").getAsString());
    			adopt.setSpecialMark(item.get("specialMark").getAsString());
    			adopt.setHappenPlace(item.get("happenPlace").getAsString());
    			adopt.setKindCd(item.get("kindCd").getAsString());
    			
    			adoptList.add(adopt);
    		}
            
            br.close();
            model.addAttribute("adoptList", adoptList); // 리스트를 뷰로 전달
            return "adopt/main"; // 뷰 이름 반환
        } else {
            return "error"; // 에러 처리
        }
		
	}
	
	
	
	
	

}
