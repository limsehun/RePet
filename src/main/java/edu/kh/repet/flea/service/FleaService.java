package edu.kh.repet.flea.service;

import edu.kh.repet.flea.dto.Flea;

import java.util.Map;

public interface FleaService {
    
    // 게시글 목록 조회
    Map<String, Object> selectFleaList(int cp);
    
    // 검색 목록 조회
    Map<String, Object> selectFleaSearchList(int cp, Map<String, Object> paramMap);
    
    // 게시글 상세 조회
    Flea selectFleaDetail(Map<String, Integer> map);
    
    // 현재 게시글이 속해있는 페이지 번호 조회
    int getCurrentPage(Map<String, Object> paramMap);
    
}
