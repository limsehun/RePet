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
  private String 	email;
  private String 	password;
  private String 	nickname;
  private String 	phone;
  private String 	memberAddress;
  private String 	profileImg;
  private String 	enrollDate;
  private String 	memberDelFl;
  private int 		authority; 
}