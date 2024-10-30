package edu.kh.repet.flea.dto;

import java.util.List;

import edu.kh.repet.board.dto.BoardImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// DTO (Data Transfer Object) : 계층간 데이터 전달용 객체
// - 계층? Controller, Service, DB 등을 구분

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Flea {
    
    // 행 번호
    private int	   rnum;
    
    // BOARD 테이블 컬럼과 매핑되는 필드
    private int    boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardWriteDate;
    private String boardUpdateDate;
    private int    readCount;
    private String boardDelFl;
    private int    memberNo;
    private int    boardCode;
    
    private String goods;
    private int    price;
    
    // MEMBER 테이블 JOIN 컬럼
    private String memberNickname;
    
    // 목록 조회 시 댓글 / 개설 채팅방 수 상관 쿼리 결과
    private int    commentCount;
    private int    chatCount;
    
    // ----------------------------------------
    
    private String thumbnail;	// 썸네일 이미지 (경로와 이름)
    private String profileImg;	// 작성자 프로필 이미지
    
    // 특정 게시글의 이미지 목록을 저장할 필드
    private List<FleaImg> imageList;
    
}
