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
import edu.kh.repet.common.util.FileUtil;
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

	@Override
	public int savePost(Board board) {
		
		int result = mapper.insertPost(board);
		
		 
       
		
		return result;
	}
	
	@Override
	public String uploadImage(MultipartFile file) {
		
		String rename = FileUtil.rename(file.getOriginalFilename());
		BoardImg boardImg = BoardImg.builder()
												.imgPath(webPath + rename)
												.imgRename(rename)
												.build();
		int result = mapper.uploadImage(boardImg);
		
		if(result == 0) throw new RuntimeException("이미지 업로드 실패");
		
		try {
			
			file.transferTo(new File(folderPath + rename));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("이미지 업로드 실패");
		}
		
		
		return webPath + rename ;
	}
	
	

	// 게시글 삭제
	@Override
	public int deleteBoard(int boardNo, int memberNo) {
		
		
		return mapper.deleteBoard(boardNo, memberNo);
	}
	
	
	@Override
	public int updateBoard(Board board) {
		
		int result = mapper.updateBoard(board);
		
		if (result == 0) {
			throw new RuntimeException("게시글 수정 실패");
		}
		
		
		return result;
	}




















}
