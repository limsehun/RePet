package edu.kh.repet.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.repet.board.dto.Board;

@Mapper
public interface BoardMapper {

	
	/** boardCode가 일치하는 게시글의 전체 개수 조회
	 * @param boardCode
	 * @return result
	/** boardCode가 일치하는 게시글 전체 대수 조회
	 * @param boardCode
	 * @return
	 */
	int getListCount(int boardCode);

	
	/**
	 * 
	 * @param boardCode
	 * @param rowBounds
	/** 지정된 페이지 분량의 게시글 목록 조회
	 * @param rowBounds
	 * @param boardCode
	 * @return
	 */
	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);


	Board boardDetail(Map<String, Integer> map);


	int updateReadCount(int boardNo);


	// 좋아요 누른 적 있나 검사
	int checkBoardLike(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo);


	// 좋아요 테이블에 삽입
	int insertBoardLike(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo);

	// 좋아요 테이블 삭제
	int deleteBoardLike(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo);


	// INSERT,DELETE 수행 후 해당 게시물 개수 조회
	int getLikeCount(int boardNo);
	














}
