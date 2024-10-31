package edu.kh.repet.manager.transaction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.manager.transaction.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{ 

	public final TransactionMapper mapper;
	
	@Override
	public Map<String, Object> selectBoardList(int cp) {

		int listCount = mapper.getListCount();

		Pagination pagination = new Pagination(cp, listCount);

		int limit = pagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<Board> boardList = mapper.selectBoardList(rowBounds);
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardList", boardList);
		map.put("pagination", pagination);
		
		return map;
	}
	
	@Override
	public Map<String, Object> selectSearchList(int cp, Map<String, Object> paramMap) {

		
		int searchCount = mapper.getSearchCount(paramMap);

		Pagination pagination = new Pagination(cp, searchCount);
		

		int limit = pagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectSearchList(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardList", boardList);
		map.put("pagination", pagination);
		
		return map;
	}
	
	
	// 중고 신고게시판
	@Override
	public Map<String, Object> selectReportList(int cp) {
		int listCount = mapper.getReportListCount();

		Pagination pagination = new Pagination(cp, listCount);

		int limit = pagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<Board> boardList = mapper.selectReportList(rowBounds);
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardList", boardList);
		map.put("pagination", pagination);
		
		return map;
	}
	
	@Override
	public Map<String, Object> selectSearchReportList(int cp, Map<String, Object> paramMap) {
		int searchCount = mapper.getSearchReportCount(paramMap);

		Pagination pagination = new Pagination(cp, searchCount);
		

		int limit = pagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectSearchReportList(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardList", boardList);
		map.put("pagination", pagination);
		
		return map;
	}
	
	

}