package edu.kh.repet.flea.service;

import edu.kh.repet.common.exception.FileDeleteFailException;
import edu.kh.repet.common.exception.FileUploadFailException;
import edu.kh.repet.common.util.FileUtil;
import edu.kh.repet.flea.dto.Flea;
import edu.kh.repet.flea.dto.FleaImg;
import edu.kh.repet.flea.mapper.EditFleaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:/config.properties")
@Service
@Transactional
@RequiredArgsConstructor
public class EditFleaServiceImpl implements EditFleaService {
    
    private final EditFleaMapper mapper;
    
    @Value("${my.board.web-path}")
    private String webPath;
    
    @Value("${my.board.folder-path}")
    private String folderPath;
    
    
    // 게시글 등록
    @Override
    public int fleaInsert(Flea inputFlea, List<MultipartFile> images) {
        
        // 1) 게시글 부분 (제목, 내용, 작성자, 게시판 종류) +  상품명, 가격
        int result = mapper.boardInsert(inputFlea);
        
        System.out.println(inputFlea.getBoardNo());
        
        // 삽입 실패 시
        if(result == 0) return 0;
        
        // 삽입된 게시글 번호
        int boardNo = inputFlea.getBoardNo();
        
//         2) FLEA_BOARD 부분 INSERT
//        result = mapper.fleaInsert(inputFlea, boardNo);
        result = mapper.fleaInsert(inputFlea);
        
//         삽입 실패 시
        if(result == 0) return 0;
        
        // 1,2 한번에 실행하는 코드 테스트
//        int result = mapper.insertBoardAndFleaBoard(inputFlea);
        
        // ----------------------------------------------------------------------------------------------------
        
        // 3) 실제로 업로드 된 이미지만 모아두기
        //    실제 업로드 된 파일 정보만 모아두는 List
        List<FleaImg> uploadList = new ArrayList<>();
        
        // images List 에서 실제 업로드 된 파일만 골라내기
        for(int i = 0; i< images.size(); i++) {
            
            // 제출된 이미지 없을 경우
            if (images.get(i).isEmpty()) continue;
            
            // 파일이 있을 경우!
            // 변경된 파일명 - 원본 저장 필요 X
            
            // 파일명 + boardNo + i
            String rename = FileUtil.rename(images.get(i).getOriginalFilename());
            
            // DB Insert 를 위한 FleaImg 객체 생성 - Board는 썸머보드 사용함
            FleaImg img = FleaImg.builder()
                    .imgRename(rename)              // 변경명
                    .imgPath(webPath)               // 웹 접근 경로
                    .boardNo(boardNo)               // 게시글 번호
                    .imgOrder(i)                    // 이미지 순서
                    .uploadFile(images.get(i))      // 실제 업로드된 이미지
                    .build();
            
            // uploadList 에 추가
            uploadList.add(img);
        }
        
        // 제출 이미지 없을경우
        if(uploadList.isEmpty()) return boardNo;
        
        // ----------------------------------------------------------------------------------------------------
        
        // 3) DB에 uploadList 에 저장된 값 모두 INSERT
        //    + transferTo() 수행해서 파일 저장
        
        // 여러 행 한번에 삽입 후 삽입된 행 개수 반환
        int insertRows = mapper.insertUploadList(uploadList);
        
        // Insert 된 행 개수와 uploadList 의 개수가 불일치
        if(insertRows != uploadList.size()) {
            throw new RuntimeException("이미지 INSERT 실패");
        }
        
        // 모두 삽입 성공 시
        // 임시저장된 이미지를 서버에 지정된 폴더에 변경명으로 저장
        try {
            
            File folder = new File(folderPath);
            if (folder.exists() == false) {
                folder.mkdirs();
            }
            
            // 게시글 이미지 모두 삽입 성공시
            for(FleaImg img : uploadList) {
                img.getUploadFile()
                        .transferTo(new File(folderPath + img.getImgRename()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 사용자 정의 예외
            throw new FileUploadFailException();
        }
        
        return boardNo;
    }
    
    
    // 게시글 삭제
    @Override
    public int fleaDelete(int memberNo, int boardNo) {
        return mapper.fleaDelete(memberNo, boardNo);
    }
    
    
    // 게시글 수정
    @Override
    public int fleaUpdate(Flea inputFlea, List<MultipartFile> images, String deleteOrderList) {
        
        // 1. 게시글 부분 수정
        int result = mapper.fleaUpdate(inputFlea);
        if(result == 0) return 0;   // 수정 실패시
        
        // 2. 기존에 존재했던 이미지 중
        //    deleteOrderList 에 존재하는 순서의 이미지를 DELETE
        
        // deleteOrderList 에 작성된 값이 있다면
        if(deleteOrderList != null && deleteOrderList.equals("") == false) {
            result = mapper.deleteImage(deleteOrderList, inputFlea.getBoardNo());
            
            // 삭제된 행이 없을 경우 ==> SQL 실패
            // ===> 예외를 발생시켜서 전체 rollback
            if(result == 0) {
                // 사용자 정의 예외
                throw new FileDeleteFailException("이미지 삭제 실패");
            }
        }
        
        // 3. 업로드된 이미지가 있을 경우
        //    UPDATE 또는 INSERT + transferTo()
        
        // 실제 업로드된 이미지만 모아두는 리스트 생성
        List<FleaImg> uploadList = new ArrayList<>();
        
        for(int i = 0; i< images.size(); i++) {
            
            // i번째 요소에 업로드된 파일이 없으면 다음으로
            if (images.get(i).isEmpty()) continue;
            
            // 업로드된 파일이 있으면
            String rename = FileUtil.rename(images.get(i).getOriginalFilename());
            
            FleaImg img = FleaImg.builder()
                    .imgRename(rename)              // 변경명
                    .imgPath(webPath)               // 웹 접근 경로
                    .boardNo(inputFlea.getBoardNo())// 게시글 번호
                    .imgOrder(i)                    // 이미지 순서
                    .uploadFile(images.get(i))      // 실제 업로드된 이미지
                    .build();
            
            // 1행씩 수행
            result = mapper.updateImage(img);
            
            // 수정 실패 == 기존에 이미지가 없었음
            // == 새 이미지가 새 order 자리에 추가 ==> INSERT
            if(result == 0) {
                result = mapper.insertImage(img);
            }
            
            // 수정, 삭제 모두 실패한 경우
            if(result == 0) {
                throw new RuntimeException("이미지 추가/수정 실패");
            }
            
             uploadList.add(img);   // 업로드된 파일 리스트에 img 추가
        }
        
        // 새 이미지 없는 경우
        if(uploadList.isEmpty()) return result;
        
        // 임시 저장된 이미지를 지정된 경로로 이동(transferTo())
        try {
            for(FleaImg img : uploadList) {
                img.getUploadFile()
                        .transferTo(new File(folderPath + img.getImgRename()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadFailException();
        }
        
        return result;
    }
}
