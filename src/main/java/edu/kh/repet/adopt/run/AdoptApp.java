package edu.kh.repet.adopt.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.kh.repet.adopt.dto.Adopt;

@Configuration
@ComponentScan
@PropertySource("classpath:/config.properties")
public class AdoptApp {

	// 발급된 인증코드 encoding
	// propertise에서 얻어오기
	@Value("${adopt.serviceKey}")
	public String serviceKey; 
	
	public static ArrayList<Adopt> main(String[] args) throws IOException {
		
		// Spring ApplicationContext 생성
        ApplicationContext context = new AnnotationConfigApplicationContext(AdoptApp.class);

        // Spring에서 관리되는 AdoptApp 빈을 가져옴
        AdoptApp app = context.getBean(AdoptApp.class);

        // 주입된 serviceKey 사용
        String url = "https://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic";
        url += "?serviceKey=" + app.serviceKey; // app 인스턴스에서 주입된 serviceKey를 사용

//        System.out.println("Generated URL: " + url);
    
		url += "&numOfRows=8";
		url += "&pageNo=1";
		url += "&_type=json";
//		System.out.println(url);

		URL requestUrl = new URL(url);
		
		HttpURLConnection urlConnection = 
				(HttpURLConnection)requestUrl.openConnection();

		urlConnection.setRequestMethod("GET");
		
		// OpenAPI 서버로 요청 보낸 후 입력 스트림을 통해 응답데이터 읽어들이기
		BufferedReader br 
			= new BufferedReader(new InputStreamReader(urlConnection
														.getInputStream()));
		
		String responseText = "";
		String line;
		while((line = br.readLine())!= null) {
//			System.out.println(line);
			responseText += line;
		}
		
//		System.out.println(responseText);
		
		
		// responseText에 담긴 문자열을 
		// (gson 라이브러리)JsonObject/JsonArray, JsonElement를 이용해서 데이터 추출
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();		
		
		// totalObj 안에 Json 객체 존재
		JsonObject responseObj = totalObj.getAsJsonObject("response");
		
		// response 속성 접근 -> 응답데이터에 있는 내용들을 JsonObject화
		JsonObject bodyObj = responseObj.getAsJsonObject("body").getAsJsonObject("items");
		
//		System.out.println(bodyObj);
		
		JsonArray itemArr = bodyObj.getAsJsonArray("item");
		
//		System.out.println(itemArr);
		
		ArrayList<Adopt> list = new ArrayList<>();
		for(int i=0; i<itemArr.size();i++) {
			JsonObject item = itemArr.get(i).getAsJsonObject();
			
//			System.out.println(item);
			
//			ObjectMapper mapper = new ObjectMapper();
//			Adopt adopt = mapper.convertValue(item, Adopt.class);
//			System.out.println(adopt);
			
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
			
			list.add(adopt);
		}
		
//		System.out.println(list);
		for(Adopt ap:list) {
				System.out.println(ap);
		}
		
		br.close();
		urlConnection.disconnect();
	
		return list;
	}
}
