package edu.kh.repet.manager.commentmanager.mapper;

import edu.kh.repet.board.dto.Comment;
import edu.kh.repet.board.dto.ReportBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface CommentManagerMapper {
    int commentCount();
    
    List<Comment> selectCommentList(RowBounds rowBounds);
    
    int reportCommentCount();
    
    List<ReportBoard> reportCommentList(RowBounds rowBounds);
    
    int deleteComment(int commentNo);
}
