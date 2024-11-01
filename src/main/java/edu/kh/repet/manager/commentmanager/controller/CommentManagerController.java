package edu.kh.repet.manager.commentmanager.controller;

import edu.kh.repet.manager.boardmanager.service.BoardManagerService;
import edu.kh.repet.manager.commentmanager.service.CommentManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("manager/comment")
@RequiredArgsConstructor
public class CommentManagerController {
    
    private final CommentManagerService service;
    
    @GetMapping("commentManagement")
    public String managerPage() {
        return "manager/board/boardManagement";
    }
    

    // 전체조회
    @ResponseBody
    @GetMapping("selectBoardList")
    public Map<String, Object> selectCommentListList(
            @RequestParam(value="cp", required = false, defaultValue = "1") int cp,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "query", required = false) String query
    ) {
        
        return service.selectCommentList(cp, key, query);
    }
    
    
    // 신고조회
    @ResponseBody
    @GetMapping("reportBoardList")
    public Map<String, Object> reportCommentList(
            @RequestParam(value="cp", required = false, defaultValue = "1") int cp
    ) {
        
        return service.reportCommentList(cp);
    }
    
    
    
    // 댓글 삭제
    @ResponseBody
    @PostMapping("delete")
    public int deleteComment (
            @RequestBody int commentNo
    ) {
        
        int result = service.deleteComment(commentNo);
        
        
        return result;
    }
    
    
    @GetMapping("commentReport")
    public String commentReport() {
        
        return "manager/comment/comment-report";
    }
    
    
    
}