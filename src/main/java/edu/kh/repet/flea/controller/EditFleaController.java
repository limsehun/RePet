package edu.kh.repet.flea.controller;

import java.util.List;
import java.util.Map;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.flea.dto.Flea;
import edu.kh.repet.flea.service.FleaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.repet.flea.service.EditFleaService;
import edu.kh.repet.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("editFlea")
@Slf4j
public class EditFleaController {
    
    // Flea <=> Board 코드 재활용 하지말것. 헷갈린다 ==> 다 만들고 변경할 것만 변경
    private final EditFleaService service;
    
    private final FleaService fleaService;
    
    /**
     * 게시글 작성 화면 전환
     * @return
     */
    @GetMapping("flea/insert")
    public String fleaInsert() {
        return "flea/fleaWrite";
    }
    
    
    @PostMapping("flea/insert")
    public String fleaInsert(
            @ModelAttribute Flea inputFlea,
            @SessionAttribute("loginMember") Member loginMember,
            @RequestParam("images") List<MultipartFile> images,
            // 요청 파라미터를 얻어와서 지정한 변수에 저장할 수 있는
            RedirectAttributes ra
    ) {
        /* 전달된 파라미터 확인 (debug 모드)
         * 제목, 내용, boardCode ==> inputBoard 커멘드 객체
         *
         * List<MultipartFile> images 의 크기(size()) == 제출된 file 타입의 input 태그 개수 == 5개
         *
         * * 선택된 파일이 없더라도 빈칸으로 제출이 된다!!!
         * ex) 0, 2, 4 인덱스만 선택 ==> 0, 2, 4 는 제출된 파일이 있고 1, 3 은 빈칸으로 존재
         *
         *  */
        
        // 1) 작성자 번호 세팅
        inputFlea.setMemberNo(loginMember.getMemberNo());
        
        // 2) 서비스 호출 결과 반환(게시글번호)
        int boardNo = service.fleaInsert(inputFlea, images);
        
        // 3) 서비스 결과에 따라 응답제어
        String path = null;
        String message = null;
        
        if(boardNo == 0) {  // 실패
            path = "insert";
            message = "게시글 작성 실패";
        } else {
            path = "/flea/" + boardNo;      // 상세 조회 주소
            message = "게시글이 등록 되었습니다";
        }
        
        ra.addFlashAttribute("message", message);
        
        return "redirect:" + path;
    }
    
    @PostMapping("delete")
    public String fleaDelete(
            @RequestParam("boardNo") int boardNo,
            @SessionAttribute("loginMember") Member loginMember,
            RedirectAttributes ra
//            ,@RequestHeader("referer") String referer
    ) {
        
        /*
           http: /   /  localhost  /  board  /  3  /  2020
           http: /   /  localhost  /   flea  / 2020/
		     0   / 1 /      2      /    3    /  4  /   5
         */
//        String regExp = "/flea/[0-9]+";  수정정 예정
         
        // 2) 서비스 호출 후 결과(게시글 번호) 반환
        int confirm = service.fleaDelete(loginMember.getMemberNo(), boardNo);
        
        // 3) 서비스 결과에 따라
        String path = null;
        String message = null;
        
        if(confirm == 0) {  // 실패 시
            path = "referer";
            message = "삭제 실패";
        } else {
            path = "/flea";
            message = "게시글이 삭제되었습니다.";
        }
        
        ra.addFlashAttribute("message", message);
        
        return "redirect:" + path;
    }
    
    /**
     * 게시글 수정 화면 전환
     */
    @PostMapping("flea/{boardNo}/updateView")
    public String updateView(
            @PathVariable("boardNo") int boardNo,
            @SessionAttribute("loginMember") Member loginMember,
            RedirectAttributes ra,
            Model model
    ) {
        int boardCode = 3;
        // boardCode, boardNo 가 일치하는 글 조회
        Map<String, Integer> map = Map.of("boardCode", boardCode, "boardNo", boardNo);
        
//        Flea flea = fleaService.selectDetail(map);
        
        Flea flea = null;
        // 게시글이 존재하지 않는 경우
        if(flea == null) {
            ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다.");
            return "redirect:/flea";
        }
        
        // 게시글 작성자가 로그인한 회원이 아닌 경우
        if(flea.getMemberNo() != loginMember.getMemberNo()) {
            ra.addFlashAttribute("message", "글 작성자만 수정 가능합니다");
            
            return String.format("redirect:/flea/%d", boardNo);		// 상세 조회
        }
        
        // 게시글이 존재하고
        // 로그인한 회원이 작성한 글이 맞을 경우
        // 수정 화면으로 forward
        model.addAttribute("flea", flea);
        return "flea/fleaUpdate";
    }
    
    @PostMapping("flea/{boardNo:[0-9]+}/update")
    public String fleaUpdate(
            @PathVariable("boardNo") int boardNo,
            @ModelAttribute Flea inputFlea,     // 제출된 파라미터 중에서 name 속성값이 같으면 저장하는 커멘드 객체
            @SessionAttribute("loginMember") Member loginMember,    // 접속자가 수정자가 맞는지
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam(value="deleteOrderList", required = false) String deleteOrderList,
            RedirectAttributes ra
    ) {
        
        // 1. 커멘드 객체 inputBoard 에 로그인한 회원 번호 추가
        inputFlea.setMemberNo(loginMember.getMemberNo());
        
        // inputFlea 에 세팅된 값
        // : boardNo, boardTitle, boardContent, memberNo
        
        // 2. 게시글 수정 서비스 호출 후 결과 반환
        int result = service.fleaUpdate(inputFlea, images, deleteOrderList);
        
        String message = null;
        if(result > 0) {
            message = "게시글이 수정 되었습니다";
        } else {
            message = "수정 실패";
        }
        
        ra.addFlashAttribute("message", message);
        
        return String.format("redirect:/flea/%d", boardNo);	// 수정 끝나면 해당 글 상세조회
        
    }
}
