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
public class AdoptSelect {

	// 조회 시 필요
	
		
		// ----------sido 조회 ------------
		/*
		 * private String orgCd; // 시도코드 
		 * private String orgdownNm; // 시도명
		 */		
		/* 이 부분이 sigungu의 uprCd(응답코드) */
		
		
		// ---------- sigungu 조회 ------------
		private String orgCd; // 시군구 코드
		private String orgdownNm; // 보호소 번호
		
		// -------------------------------------------- 
		private String upkind; // 축종코드
		/* 개 : 417000, 고양이 : 422400, 기타 : 429900*/
		
		
}
