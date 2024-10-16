package edu.kh.repet.adopt.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdoptApp {

	// 발급된 인증코드 encoding
	public static final String serviceKey 
		= "h8joTLPw3bNJHG2%2Fl2ZCzBIfpYYuJD5%2FDhcxfNpkcQdPX7cZr%2Bj%2BXPS5uFj7Qah9jfjJvL6IS%2FYaH7adjebC%2Bw%3D%3D";
	
	public static void main(String[] args) throws IOException {
		
		// 공통된 url 부분		
		String url 
			="https://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic";
	
		url += "?serviceKey=" + serviceKey;
		url += "&numOfRows=10";
		url += "&pageNo=1";
		url += "&dataType=json";
		LocalDateTime datetime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		url += "&endde=" + datetime.format(formatter);
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
		
		// gson 다운로드 [아키텍쳐 -29p-]
		
		
		
		
		
	}
}
