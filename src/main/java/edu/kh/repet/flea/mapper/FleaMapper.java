package edu.kh.repet.flea.mapper;

import edu.kh.repet.flea.dto.Flea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface FleaMapper {
    
    // 목록조회 - 게시글 전체 수 조회
    int getListCount(int boardCode);

    // 목록조회 - cp에 해당하는
    List<Flea> selectFleaList(int boardCode, RowBounds rowBounds);

    // 상세조회
    Flea selectDetail(Map<String, Integer> map);

    // 조회수 증가
    int updateReadCount(int boardNo);

    // 현재 게시글이 속해있는 페이지 번호 조회하는 서비스
    int getCurrentPage(Map<String, Object> paramMap);
}
