package edu.kh.repet.flea.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleaImg {
    
    // BOARD_IMG 컬럼과 매핑되는 필드
    private int		imgNo;
    private String	imgPath;
    private String	imgRename;
    private int		boardNo;
    private int     imgOrder;
    
    // 게시글 이미지 삽입 / 수정 시 사용할 필드
    private MultipartFile uploadFile;	// 개발 편의성 향상
    
}
