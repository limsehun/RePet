package edu.kh.repet.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;

@Mapper
public interface EditBoardMapper {

	// 이미지 정보 저장 메서드
	void insertBoardImg(BoardImg boardImg);

	// 게시글 저장 메서드
	int insertBoard(Board board);

	
	
}
