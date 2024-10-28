package edu.kh.repet.board.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardImg {

	
	// BOARD_IMG 테이블 컬럼명과 매핑되는 필드
	private int    imgNo;        // 이미지 번호
	private String imgPath;     // 이미지 경로
	private String imgRename;  // 이미지 변경명
	private int    boardNo;   // 이미지가 첨부된 게시글 번호
	
	private MultipartFile uploadFile; // (개발 편의성 향상)

}
