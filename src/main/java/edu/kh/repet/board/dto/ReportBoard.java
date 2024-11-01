package edu.kh.repet.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReportBoard {

   private int memberNo; // 신고한 회원 번호
  private int boardNo; // 게시글 번호
  private String reportCategoryContent; // 신고 내용
  private String reportWriteDate; // 신고 일자 (자동 설정)
  private int progressCode; // 신고 상태 (기본값 1)
  private int reportCategory; // 신고 카테고리
  private String reportNickname; 
  private int reportCount;

}
