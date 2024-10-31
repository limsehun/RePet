package edu.kh.repet.manager.boardmanager.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.board.dto.ReportBoard;
import edu.kh.repet.manager.boardmanager.mapper.BoardManagerMapper;
import edu.kh.repet.member.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardManagerServiceImpl implements BoardManagerService{
   
   private final BoardManagerMapper mapper;
   


	// 게시물 리스트 조회
	@Override
	public  Map<String, Object> selectBoardList(int cp) {
		
		int boardCount = mapper.boardCount();
		
		Pagination pagination = new Pagination(cp, boardCount, 10, 5);
		
		int offset = (cp - 1) * pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Board> boardList = mapper.selectBoardList(rowBounds);
		
		Map<String, Object> map = Map.of("boardList", boardList, "pagination", pagination);
		
		return map;
	}
	
	
	@Override
	public int deleteBoard(int boardNo) {
		return mapper.deleteBoard(boardNo);
	}
	
	
	@Override
	public Map<String, Object> reportBoardList(int cp) {
		
		int reportCount = mapper.reportCount();
		
		Pagination pagination = new Pagination(cp, reportCount, 10, 5);
		
		int offset = (cp - 1) * pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<ReportBoard> reprotBoardList = mapper.reportBoardList(rowBounds);
		
		Map<String, Object> map = Map.of("reprotBoardList", reprotBoardList, "pagination", pagination, "reportCount", reportCount);
		
		System.out.println(map);
		
		return map;
	}


}
