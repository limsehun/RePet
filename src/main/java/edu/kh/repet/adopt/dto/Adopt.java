package edu.kh.repet.adopt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Adopt {
	
	
	
	// 상세 내역 < 5) [유기동물 조회] 상세기능명세 >

	private String popfile; // 사진
	
	private String age; // 나이
	private String neuterYn; // 중성화 여부
	private String sexCd; // 성별
	
	private String careNm; // 보호소이름
	private String careTel; // 보호소 전화번호
	private String careAddr; // 보호장소
	
	private String happenPlace; // 발견장소
	private String processState; // 상태(공고중/보호중)
	private String noticeNo; // 공고번호
	private String noticeSdt; // 공고시작일
	private String noticeEdt; // 공고종료일
	private String specialMark; // 특징
	private String kindCd; // 품종코드
	
	
	
	
	
}
