package edu.kh.repet.manager.commentmanager.service;

import java.util.Map;

public interface CommentManagerService {
    
    Map<String, Object> selectCommentList(int cp, String key, String query);
    
    Map<String, Object> reportCommentList(int cp);
    
    int deleteComment(int currentCommentNo);
}
