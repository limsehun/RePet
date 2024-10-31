package edu.kh.repet.flea.service;


import edu.kh.repet.flea.dto.Flea;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EditFleaService {
    
    // 게시글 등록
    int fleaInsert(Flea inputFlea, List<MultipartFile> images);
    
    // 게시글 삭제
    int fleaDelete(int memberNo, int boardNo);
    
    // 게시글 수정
    int fleaUpdate(Flea inputFlea, List<MultipartFile> images, String deleteOrderList);
}
