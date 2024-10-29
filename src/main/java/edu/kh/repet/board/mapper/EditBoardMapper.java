package edu.kh.repet.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;

@Mapper
public interface EditBoardMapper {


	int insertPost(Board board);

	
	int uploadImage(BoardImg boardImg);




	
	
}
