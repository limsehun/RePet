package edu.kh.repet.board.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.board.service.BoardService;
import edu.kh.repet.member.dto.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService service;
	
	/** 게시글 목록 조회
	 * @param boardCode : 게시판 번호
	 * @param cpage : 현재 조회하려는 목록의 페이지 번호 (필수 아님, 없으면 1) 
	 * @param model : forward 시 데이터 전달하는 용도의 객체(request)
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(
			@PathVariable("boardCode") int boardCode,
			@RequestParam(value = "cpage", required = false, defaultValue = "1") int cpage,
			Model model,
			@RequestParam Map<String, Object> paramMap
			) {
		
		// 서비스 호출 후 결과 반환 받기
	  // - 목록 조회인데 Map으로 반환 받는 이유?
		//  -> 서비스에서 여러 결과를 만들어 내야되는데
		//     메서드는 반환을 1개만 할 수 있기 때문에
		//     Map으로 묶어서 반환 받을 예정
		Map<String, Object> map =  null;
		
		if (paramMap.get("key") == null) {
	    map = service.selectBoardList(boardCode, cpage);
	} else {
	    map = service.searchBoard(boardCode, cpage, paramMap);
	}

		
		
		// Map에 묶인 값 풀어놓기
		List<Board> boardList = (List<Board>)map.get("boardList");
		Pagination pagination = (Pagination)map.get("pagination");
		
		// log 확인
    //		for(Board b : boardList) log.debug(b.toString());
    //		log.debug(pagination.toString());
	 	
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagination", pagination);
		
//		log.debug("Pagination: " + pagination.toString());
		
		if(boardCode == 3) return "redirect:/flea";
		
		return "board/boardList";
	}
	
	// 게시글 상세조회
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
	        @PathVariable("boardCode") int boardCode,
	        @PathVariable("boardNo") int boardNo,
	        Model model,
	        @SessionAttribute(value = "loginMember", required = false) Member loginMember,
	        RedirectAttributes ra,
	        HttpServletRequest req,
	        HttpServletResponse resp) {
		
		
		

	    Map<String, Integer> map = new HashMap<>();
	    map.put("boardCode", boardCode);
	    map.put("boardNo", boardNo);
	    
	    if(loginMember != null) {
	    	map.put("memberNo", loginMember.getMemberNo());
	    }

	    Board board = service.boardDetail(map);

	    // 게시글 상세 조회 결과가 없을 경우
	    if (board == null) {
	        ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
	        return "redirect:/board/" + 2;
	    }
	    
	    /* -------------------- 조회 수 증가 --------------------- */
	    if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
	    	
	    	// 쿠키 얻어오기
	    	Cookie[] cookies = null;
	    	Cookie viewCookie = null;
	    	
	    	if(req.getCookies() != null ) {
	    		cookies = req.getCookies();
	    		
	    		for(Cookie cookie : cookies) {
	    			if(cookie.getName().equals("readBoardNo")) {
	    				viewCookie = cookie;
	    				break;
	    			}
	    		}
	    	}
	    	
	    	int result = 0;
	    	
	   // 이전에 readBoardNo라는 name을 가지는 쿠키가 없을 경우
				if(viewCookie == null) {
					// 새 쿠키를 생성
					// readBoardNo=[1000][2000][3000]
					viewCookie = new Cookie("readBoardNo", "[" + boardNo + "]");
					
					/* DB에서 해당 게시글의 조회 수를 1 증가 시키는 서비스 호출 */
					result = service.updateReadCount(boardNo);
				}
				// 이전에 readBoardNo라는 name을 가지는 쿠키가 있을 경우
				else {
					// readBoardNo=[1000][2000][3000]
					
					// 현재 읽은 게시글 번호가 쿠키에 없다면
					// == 해당 글은 처음 읽음
					if(viewCookie.getValue().contains(boardNo + "") == false) {
						
						viewCookie.setValue(viewCookie.getValue() + "[" + boardNo + "]");
						
							// DB에서 조회 수 증가
							result = service.updateReadCount(boardNo);
							
							
					}
				}
	    	
	    	// 조회수가 증가한 경우 쿠키 설정 및 응답에 추가
	    	if(result > 0) {
	    		board.setReadCount(board.getReadCount() + 1);
	    		viewCookie.setPath("/");
	    		
	    		// 다음날 자정까지 남은 시간 계산
	    		LocalDateTime now = LocalDateTime.now();
	    		LocalDateTime tomorrowMidnight = now.plusDays(1).truncatedTo(ChronoUnit.DAYS);
	    		long secondsUntilMidnight = ChronoUnit.SECONDS.between(now, tomorrowMidnight);
	    		
	    		viewCookie.setMaxAge((int) secondsUntilMidnight);
	    		resp.addCookie(viewCookie);
	    		
	    	}
	    	
	    }

	    // 조회된 게시글 정보를 모델에 추가하여 View로 전달
	    model.addAttribute("board", board);
	    
	 // 조회수가 높은 게시물 5개를 조회하여 모델에 추가
	    List<Board> top5Boards = service.getTop5Boards();
	    model.addAttribute("top5Boards", top5Boards);
	    
	    if(boardCode == 3) return "redirect:/flea";

	    return "board/boardDetail"; // 게시글 상세 페이지로 이동
	}
	
	/** 좋아요 체크 or 해제
	 * @param boardNo
	 * @return map (check, clear / 좋아요 개수)
	 */
	@ResponseBody
	@PostMapping("like")
	public Map<String, Object> boardLike(
			@RequestBody int boardNo,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		int memberNo = loginMember.getMemberNo();
		
		return service.boardLike(boardNo, memberNo);
	}
	
	
	// 게시글 목록으로 리다이렉트
	@GetMapping("{boardCode}/{boardNo}/goToList")
	public String goToList(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("boardNo") int boardNo,
			@RequestParam Map<String, Object> paramMap
			) {
		
		paramMap.put("boardCode", boardCode);
		paramMap.put("boardNo", boardNo);
		
		int cpage = service.getCurrentPage(paramMap);
		
		String url = "redirect:/board/" + boardCode + "?cpage=" + cpage;
		return url;
	}
	
	





	
	
	











}
