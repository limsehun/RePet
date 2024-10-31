package edu.kh.repet.flea.mapper;

import edu.kh.repet.flea.dto.Flea;
import edu.kh.repet.flea.dto.FleaImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EditFleaMapper {
    
    /**
     * 게시'글' 부분 INSERT
     * @param inputFlea
     * @return result
     */
    int boardInsert(Flea inputFlea);
    
    /**
     * '중고거래' 부분 INSERT
     * @param inputFlea
     * @param boardNo
     * @return
     */
    int fleaInsert(@Param("inputFlea") Flea inputFlea, @Param("boardNo") int boardNo);
    
    
    /**
     * 여러 이미지 한 번에 INSERT
     * @param uploadList
     * @return insertRows
     */
    int insertUploadList(List<FleaImg> uploadList);
    
    /**
     * 게시글 삭제
     * @param memberNo
     * @param boardNo
     * @return
     */
    int fleaDelete(int memberNo, int boardNo);
    
    // ----- 게시글 수정 -----
    
    /** 1-1
     * 게시글 수정(제목/내용)
     * @param inputFlea
     * @return
     */
    int fleaUpdate(Flea inputFlea);
    
    /** 1-2
     * 게시글 기존 이미지 삭제
     * @param deleteOrderList
     * @param boardNo
     * @return
     */
    int deleteImage(String deleteOrderList, int boardNo);
    
    /** 3-1
     * 이미지 1행 수정
     * @param img
     * @return
     */
    int updateImage(FleaImg img);
    
    /** 3-2
     * 새로운 이미지 1행 삽입
     * @param img
     * @return
     */
    int insertImage(FleaImg img);
    

}
