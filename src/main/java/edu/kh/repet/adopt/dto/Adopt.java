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
	
	// 조회 시 필요
	
	private String upkind; // 축종코드
	/* 개 : 417000, 고양이 : 422400, 기타 : 429900*/
	private String kind; // 품종코드
	
	private String upr_cd; // 시도코드
	private String org_cd; // 시군구 코드
	private String care_reg_no; // 보호소 번호
	
	// 상세 내역 

	private String age; // 나이
	private String neuter_yn; // 중성화 여부
	private String sexCd; // 성별
	
	private String careNm; // 보호소이름
	private String careTel; // 보호소 전화번호
	private String careAddr; // 보호장소
	
	
	private String state; // 상태(공고중/보호중)
	private String noticeNo; // 공고번호
	private String noticeSdt; // 공고시작일
	private String noticeEdt; // 공고종료일
	private String happenPlace; // 발견장소 
	private String specialMark; // 특징
	
	
	
}
