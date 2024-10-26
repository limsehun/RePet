package edu.kh.repet.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;
import edu.kh.repet.board.mapper.EditBoardMapper;
import lombok.RequiredArgsConstructor;

@PropertySource("classpath:/config.properties")
@Transactional
@Service
@RequiredArgsConstructor
public class EditBoardServiceImpl implements EditBoardService {

	private final EditBoardMapper mapper;
	
	@Value("${my.board.web-path}")
	private String webPath;

	@Value("${my.board.folder-path}")
	private String folderPath;

	// 이미지 저장 처리
	@Override
	public List<BoardImg> saveImages(MultipartFile[] files, int boardNo) {
		
		List<BoardImg> boardImgs = new ArrayList<>();
		
		for( MultipartFile file : files ) {
			
			try {
				
				// 파일명 중복 되지 않게 랜덤 문자열 지정
				String originalFileName = file.getOriginalFilename();
				String extension        = originalFileName.substring(originalFileName.lastIndexOf("."));
				String savedFileName    = UUID.randomUUID().toString() + extension;
				
				// 파일 저장 없다면 폴더 생성 
				File saveFile = new File(folderPath, savedFileName);
				if(saveFile.exists() == false) { // 폴더가 없을 경우
					saveFile.mkdirs(); // 폴더 생성
				}
				file.transferTo(saveFile);
				
				// BoardImg 객체 생성
				BoardImg boardImg = BoardImg.builder()
						.imgPath(webPath + savedFileName) // 웹 경로 설정
						.imgRename(savedFileName) // 변경된 파일명 설정
						.boardNo(boardNo)  // 게시글 번호 설정
						.build();
				
				
				boardImgs.add(boardImg);
				mapper.insertBoardImg(boardImg);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
			
			
			
		}
		
		return boardImgs;
	}
	

	// 글등록 처리
	@Override
	public boolean register(Board board) {
		return mapper.insertBoard(board) > 0;
	}
	




















}
