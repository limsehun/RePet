package edu.kh.repet.flea.mapper;

import edu.kh.repet.flea.dto.Flea;
import edu.kh.repet.flea.dto.FleaImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EditFleaMapper {
    
    // 게시글 Board Table 부분 등록
    int boardInsert(Flea inputFlea);
    
    // Flea_Board Table 부분 등록
    int fleaInsert(Flea inputFlea);
//    int fleaInsert(Flea inputFlea, int boardNo);
    
    // 테스트용
//int insertBoardAndFleaBoard(Flea inputFlea);
    
    // 이미지 여러개 삽입
    int insertUploadList(List<FleaImg> uploadList);
    
    // 게시글 삭제
    int fleaDelete(int memberNo, int boardNo);
    
    // 게시글 부분 수정
    int fleaUpdate(Flea inputFlea);
    
    // 기존에 존재하던 이미지 'DB' 에서 삭제 (서버 경로 폴더에는 여전히 존재)
    int deleteImage(String deleteOrderList, int boardNo);
    
    // 이미지 1행 수정
    int updateImage(FleaImg img);
    
    // 새 이미지 1행 삽입
    int insertImage(FleaImg img);
}
