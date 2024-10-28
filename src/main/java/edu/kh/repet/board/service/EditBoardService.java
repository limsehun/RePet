package edu.kh.repet.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;

public interface EditBoardService {

	int savePost(Board board) throws IOException;

	String uploadImage(MultipartFile file);




	

	
	
	

}
