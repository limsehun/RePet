package edu.kh.repet.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper mapper;

	
	// 게시글 목록 조회
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cpage) {
		
		// 1. boardCode가 일치하는 게시글의 전체 개수 조회
		//    (조건 : 삭제되지 않은 글만 카운트!)
		int result = mapper.getListCount(boardCode);
		
		// 2) listCount와 cpage를 이용해서
		//    조회될 목록 페이지, 출력할 페이지네이션의 값을 계산할
		//    pagenation 객체 생성하기
		Pagination pagination = new Pagination(cpage, result);
		
		// 3) DB에서 cpage (조회 하려는 페이지)에 해당하는 행을 조회
		// ex) cpage == 1, 전체 목록 중 1~5행 결과만 반환
		// ex) cpage == 2, 전체 목록 중 6~10행 결과만 반환
		// ex) cpage == 10, 전체 목록 중 45~50행 결과만 반환
			
		/* [RowBounds 객체]
		 * - Mybatis 제공 객체
		 * 
		 * - 지정된 크기(offset) 만큼 행을 건너 뛰고
		 *   제한된 크기(limit) 만큼의 행을 조회함
		 *   
		 * - 사용법 : Mapper의 메서드 호출 시
		 *           2번째 이후 매개변수로 전달
		 *           (1번은 SQL에 전달할 파라미터가 기본값)
		 * */
		int limit = pagination.getLimit();
		int offset = (cpage - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectBoardList(boardCode, rowBounds);
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		
		return map;
	}
		


















}
