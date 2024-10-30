package edu.kh.repet.board.dto;


import java.util.List;

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
public class Board {
  private int rnum;
  
  
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate;
	private int readCount;
	private String boardDelFl;
	private int memberNo;
	private int boardCode;
	private String memberNickname;
	
	
	//목록 조회 시 댓글/ 좋아요 수 상관 쿼리 결과
	private int commentCount;
	private int likeCount;
	
//좋아요 체크 여부를 저장할 필드(1 == 누른적 있음)
	private int likeCheck;
	
	
	private String profileImg; // 작성자 프로필 이미지

  







}
