package edu.kh.repet.manager.boardmanager.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.board.dto.ReportBoard;
import edu.kh.repet.manager.boardmanager.mapper.BoardManagerMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardManagerServiceImpl implements BoardManagerService{
	
	private final BoardManagerMapper mapper;
	

	// 게시물 리스트 조회
	@Override
	public  Map<String, Object> selectBoardList(int cp) {
		
		int boardCount = mapper.boardCount();
		
		Pagination pagination = new Pagination(cp, boardCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectBoardList(rowBounds);
		
		Map<String, Object> map = Map.of("boardList", boardList, "pagination", pagination);
		
		return map;
	}
	
	
	@Override
	public int deleteBoard(int boardNo) {
		return mapper.deleteBoard(boardNo);
	}
	
	// 중고 신고 계시판	
	@Override
	public Map<String, Object> reportBoardList(int cp) {
		
		int reportCount = mapper.reportCount();
		
		Pagination pagination = new Pagination(cp, reportCount, 10, 5);
		
		int offset = (cp - 1) * pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<ReportBoard> reportBoardList = mapper.reportBoardList(rowBounds);
		
		Map<String, Object> map = Map.of("reportBoardList", reportBoardList, "pagination", pagination, "reportCount", reportCount);
		
		System.out.println(map);
		
		return map;
	}
	

	@Override
	public Map<String, Object> searchBoard(int cp, Map<String, Object> paramMap) {
		
		int searchCount = mapper.getSearchCount(paramMap);
		
		Pagination pagination = new Pagination(cp , searchCount);
		
		int limit = pagination.getLimit();
		int offset = (cp -1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
	// 검색 조건에 맞는 게시물 리스트 조회
    List<Board> boardList = mapper.searchBoardList(paramMap, rowBounds);

    // 검색 결과를 Map으로 반환
    Map<String, Object> resultMap = Map.of("boardList", boardList, "pagination", pagination);
    
    return resultMap;
	}



}
