package edu.kh.repet.adopt.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.kh.repet.adopt.dto.Adopt;
import edu.kh.repet.adopt.dto.AdoptPagination;

@Service
public class AdoptServiceImpl implements AdoptService{

	
	@Override
	public Map<String, Object> selectAdoptList(String urlStr, int cp) throws IOException {
		
		ArrayList<Adopt> adoptList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
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
            while ((line = br.readLine()) != null) {response.append(line);}
            
    		JsonObject totalObj = JsonParser.parseString(response.toString()).getAsJsonObject();		
    		JsonObject responseObj = totalObj.getAsJsonObject("response");
    		JsonObject bodyObj = responseObj.getAsJsonObject("body").getAsJsonObject("items");
    		JsonArray itemArr = bodyObj.getAsJsonArray("item");
    		
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
    		

	    	AdoptPagination pagination = new AdoptPagination(cp, adoptList.size());
			
			int limit = pagination.getLimit();
			int offset = (cp-1) * limit;
			
			RowBounds rowBounds = new RowBounds(offset, limit);
		
			map.put("adoptList",adoptList);
			map.put("pagination",pagination);
			
	        }
		
        return map;
	}
}
	

