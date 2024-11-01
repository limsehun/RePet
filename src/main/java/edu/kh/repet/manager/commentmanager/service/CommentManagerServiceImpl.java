package edu.kh.repet.manager.commentmanager.service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Comment;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.board.dto.ReportBoard;
import edu.kh.repet.manager.commentmanager.mapper.CommentManagerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentManagerServiceImpl implements CommentManagerService {
    
    private final CommentManagerMapper mapper;
    
    
    @Override
    public Map<String, Object> selectCommentList(int cp, String key, String query) {
        int boardCount = mapper.commentCount();
        
        Pagination pagination = new Pagination(cp, boardCount, 10, 5);
        
        int offset = (cp - 1) * pagination.getLimit();
        
        RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
        
        List<Comment> commentList = mapper.selectCommentList(rowBounds);
        
        Map<String, Object> map = Map.of("commentList", commentList, "pagination", pagination);
        
        return map;
    }
    
    @Override
    public Map<String, Object> reportCommentList(int cp) {
        int reportCommentCount = mapper.reportCommentCount();
        
        Pagination pagination = new Pagination(cp, reportCommentCount, 10, 5);
        
        int offset = (cp - 1) * pagination.getLimit();
        
        RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
        
        List<ReportBoard> reportCommentList = mapper.reportCommentList(rowBounds);
        
        Map<String, Object> map = Map.of("reportCommentList", reportCommentList, "pagination", pagination, "reportCommentCount", reportCommentCount);
        
        System.out.println(map);
        
        return map;
    }
    
    
    @Override
    public int deleteComment(int commentNo) {
        return mapper.deleteComment(commentNo);
    }
}
