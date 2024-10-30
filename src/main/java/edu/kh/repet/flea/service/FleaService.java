package edu.kh.repet.flea.service;

import edu.kh.repet.flea.dto.Flea;

import java.util.Map;

public interface FleaService {
    
    // 목록조회
    Map<String, Object> selectFleaList(int boardCode, int cp);

    // 상세 조회
    Flea selectFleaDetail(Map<String, Integer> map);

    // 조회수
    int updateReadCount(int boardNo);

    // 현재 게시글은 몇페이지에?
    int getCurrentPage(Map<String, Object> paramMap);
}
