package edu.kh.repet.flea.service;

import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.flea.dto.Flea;
import edu.kh.repet.flea.mapper.FleaMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service        // 비즈니스 로직, Bean 등록 ==> 필요한 곳에 의존성 주입
@RequiredArgsConstructor        // final 필드 생성자 자동 완성
public class FleaServiceImpl implements FleaService {

    private final FleaMapper mapper;
    
    // 중고거래 게시글 목록 조회
    @Override
    public Map<String, Object> selectFleaList(int cp) {
        
        // 1. boardCode 가 3인 게시글 전체 개수 조회
        int listCount = mapper.getFleaListCount();
        
        // listCount 와 cp를 이용해서 페이지네이션
        Pagination pagination = new Pagination(cp, listCount);
        
        // 3. DB 에서 cp에 해당하는 행 조회
        int limit = pagination.getLimit();      // 5
        int offset = (cp -1) * limit;
        
        RowBounds rowBounds = new RowBounds(offset, limit);
        
        List<Flea> fleaList = mapper.selectFleaList(rowBounds);
        
        // 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶어서 반환
        Map<String, Object> map = new HashMap<>();
        map.put("fleaList", fleaList);
        map.put("pagination", pagination);
        
        return map;
    }
    
    // 검색 목록 조회
    @Override
    public Map<String, Object> selectFleaSearchList(int cp, Map<String, Object> paramMap) {
        
        // 1. 중고거래 게시판(boardCode = 3) 에서 검색조건이 일치하는 게시글이 몇 개나 존재하는지 조회
        int searchCount = mapper.getSearchCount(paramMap);
        
        // 2. Pagination 객체 생성
        Pagination pagination = new Pagination(cp, searchCount);
        
        // 3. DB에서 cp에 해당하는 페이지 조회
        int limit = pagination.getLimit();
        int offset = (cp -1) * limit;
        RowBounds rowBounds = new RowBounds(offset, limit);
        
        // 4. 검색 결과 조회 결과 + Pagination 객체를 Map으로 묶어서 반환
        List<Flea> fleaList = mapper.selectFleaSearchList(paramMap, rowBounds);
        
        Map<String, Object> map = new HashMap<>();
        map.put("fleaList", fleaList);
        map.put("pagination", pagination);
        
        return map;
    }
    
    // 게시글 상세 조회
    @Override
    public Flea selectFleaDetail(Map<String, Integer> map) {
        return mapper.selectFleaDetail(map);
    }
    
    // 현재 게시글이 속해있는 페이지 번호 조회하는 서비스
    @Override
    public int getCurrentPage(Map<String, Object> paramMap) {
        return mapper.getCurrentPage(paramMap);
    }
}

/*

  public class Pagination {

	private int currentPage;		// 현재 페이지 번호
	private int listCount;			// 전체 게시글 수

	private int limit = 10;			// 한 페이지 목록에 보여지는 게시글 수 (5개 두줄)
	private int pageSize = 10;		// 보여질 페이지 번호 개수

	private int maxPage;			// 마지막 페이지 번호
	private int startPage;			// 보여지는 맨 앞 페이지 번호
	private int endPage;			// 보여지는 맨 뒤 페이지 번호

	private int prevPage;			// 이전 페이지 모음의 마지막 번호
	private int nextPage;			// 다음 페이지 모음의 시작 번호

*/