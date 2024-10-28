package edu.kh.repet.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.board.dto.Board;

@Mapper
public interface EditBoardMapper {


	int insertPost(Board board);


	
	
}
