package edu.kh.repet.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;

public interface EditBoardService {

	// 이미지 저장 처리
	List<BoardImg> saveImages(MultipartFile[] files, int boardNo);

	// 글 등록 처리
	boolean register(Board board);

}
