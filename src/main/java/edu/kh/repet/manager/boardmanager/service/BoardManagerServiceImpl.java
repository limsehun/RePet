package edu.kh.repet.manager.boardmanager.service;

import java.util.HashMap;
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
   public  Map<String, Object> selectBoardList(int cp, String key, String query) {
      
      int boardCount = mapper.boardCount(key, query);
      
      Pagination pagination = new Pagination(cp, boardCount, 10, 5);
      
      int offset = (cp - 1) * pagination.getLimit();
      
      RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
      
      List<Board> boardList = mapper.selectBoardList(key, query, rowBounds);
      
      
      return Map.of("boardList", boardList, "pagination", pagination);
   }
   
   
   @Override
   public int deleteBoard(int boardNo) {
      return mapper.deleteBoard(boardNo);
   }
   
   
   
   

}
