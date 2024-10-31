package edu.kh.repet.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder // 빌더 패턴 : 객체 생성 + 초기화를 쉽게하는 패턴
public class Member {

  private int 		memberNo;
  private String 	memberEmail;
  private String 	memberPw;
  private String 	memberNickname;
  private String 	memberAddress;
  private String 	profileImg;
  private String 	enrollDate;
  private String 	memberDelFl;
  private int 		authority; 
  
  /* 추가 */
  private int boardCount;
  private int commentCount;
  
  private String statusContent;
  private int reportCount;
}
