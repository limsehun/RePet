package edu.kh.repet.flea.mapper;

import edu.kh.repet.flea.dto.Flea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface FleaMapper {
    
    // boardCode 가 3인 게시글 전체 개수 조회
    int getFleaListCount();
    
    // DB 에서 cp에 해당하는 행 조회
    List<Flea> selectFleaList(RowBounds rowBounds);
    
    
    // 중고거래 게시판(boardCode = 3) 에서 검색조건이 일치하는 게시글이 몇 개나 존재하는지 조회
    int getSearchCount(Map<String, Object> paramMap);
    
    // 검색 결과 조회 결과 + Pagination 객체를 Map으로 묶어서 반환
    List<Flea> selectFleaSearchList(Map<String, Object> paramMap, RowBounds rowBounds);
    
    
    // 게시글 상세 조회
    Flea selectFleaDetail(Map<String, Integer> map);
    
    
    // 현재 게시글이 속해있는 페이지 번호 조회하는 서비스
    int getCurrentPage(Map<String, Object> paramMap);
}
