package edu.kh.repet.flea.controller;

import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.board.service.BoardService;
import edu.kh.repet.flea.dto.Flea;
import edu.kh.repet.flea.service.FleaService;
import edu.kh.repet.member.dto.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("flea")
public class FleaController {

    private final FleaService service;
    private final BoardService bservice;

    /**
     * 게시글 목록 조회
     * @param cp		: 현재 조회하려는 목록의 페이지 번호 (current page) (필수 아님, 없으면 1)
     * @param model     : forward 시 데이터 전달하는 용도의 객체(request)
     */
    @GetMapping("")
    public String selectFleaList(
            @RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
            Model model,
            @RequestParam Map<String, Object> paramMap
    ) {

        log.debug("paramMap : {}", paramMap);

        Map<String, Object> map = null;

        if(paramMap.get("key") == null) {
            // 서비스 호출 후 결과 반환 받기
            // - 목록 조회인데 Map으로 반환 받는 이유?
            // ==> 서비스에서 여러 결과를 만들어 내야 되는데
            //     메서드는 반환을 1개만 할 수 있기 때문에 Map으로 묶어서 반환 받을 예정
            map = service.selectFleaList(cp);

        } else {
            map = service.selectFleaSearchList(cp, paramMap);
        }

        // map에 묶여있는 값 풀어놓기
        List<Flea> fleaList = (List<Flea>)map.get("fleaList");
        Pagination pagination = (Pagination)map.get("pagination");  // board 에 있는 pagination 재활용

        model.addAttribute("fleaList", fleaList);
        model.addAttribute("pagination", pagination);

        return "flea/fleaList";
    }

    /**
     * 게시글 상세 조회
     * @param boardNo     : 게시글 번호
     * @param model		  : forward 시 request scope 값 전달 객체
     * @param ra		  : redirect 시 request scope 값 전달 객체
     * @param loginMember : 로그인한 회원 정보, 로그인 안되어 있으면 null
     * @param req		  : 요청 관련 데이터를 담고 있는 객체 (쿠키 포함)
     * @param resp		  : 응답 방법을 담고 있는 객체 (쿠키 생성, 쿠키를 클라이언트에게 전달)
     *                    
     * @throws ParseException : Date nextDay 요구사항
     */
    @GetMapping("/{boardNo:[0-9]+}")
    public String fleaDetail(
            @PathVariable("boardNo") int boardNo,
            Model model,
            RedirectAttributes ra,
            @SessionAttribute(value="loginMember", required=false) Member loginMember,
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ParseException {
        
        // 1) SQL 수행에 필요한 파라미터들 Map 으로 묶기
        Map<String, Integer> map = new HashMap<>();

        map.put("boardNo",boardNo);

        /* 로그인이 되어있는 경우 memberNo를 map에 추가 */
        if(loginMember != null) {
            map.put("memberNo", loginMember.getMemberNo());
        }

        // 2) 서비스 호출 후 결과 반환 받기
        Flea flea = service.selectFleaDetail(map);

        /* 게시글 상세 조회 결과가 없을 경우 */
        if(flea == null) {
            ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
            return "redirect:/flea";
            // board/3 이 boardController 통해 flea로 다시 옴
        }

        /* ---------- 조회 수 증가 시작 ---------- */
        
        // 로그인한 회원이 작성한 글이 아닌 경우 + 비회원
        if(loginMember == null || loginMember.getMemberNo() != flea.getMemberNo()) {

            // 1. 요청에 담겨있는 모든 쿠키 얻어오기
            Cookie[] cookies = null;

            Cookie c = null;
            if(req.getCookies() != null) {
                cookies = req.getCookies();

                for (Cookie temp : cookies) {
                    // Cookie는 Map 형식 (name = Value)

                    // 클라이언트로 부터 전달 받은 쿠키에
                    // "readBoardNo" 라는 key(name)가 존재하는 경우
                    // == 기존에 읽은 게시글 번호를 저장한 쿠키가 있는 경우
                    if(temp.getName().equals("readBoardNo")) {
                        c = temp;
                        break;
                    }
                }
            }

            // service 호출 결과 저장
            int result = 0;

            // 이전에 "readBoardNo" 라는 name을 가지는 쿠키가 없을 경우
            if(c == null) {
                // 새 쿠키 생성
                c = new Cookie("readBoardNo", "[" + boardNo + "]");

                /* DB 에서 해당 게시글의 조회 수를 1 증가 시키는 서비스 호출 */
                result = bservice.updateReadCount(boardNo);
            }
            // 이전에 "readBoardNo" 라는 name을 가지는 쿠키가 있을 경우
            else {
                // readBoardNo = [1000][2000][3000]

                // 현재 읽은 게시글 번호가 쿠키에 없다면
                // == 해당 글은 처음 읽음
                if(c.getValue().contains(boardNo+"") == false) {
                    c.setValue(c.getValue() + "[" + boardNo + "]");

                    // DB에서 조회 수 증가
                    result = bservice.updateReadCount(boardNo);
                }
            }


            // 2. 조회 수가 증가된 경우 쿠키 세팅하기
            if(result > 0) {

                // 미리 조회된 조회 수 와 DB 조회 수 동기화
                flea.setReadCount(flea.getReadCount() + 1);

                // 읽은 글 번호가 저장된 쿠키(c)가
                // 어떤 주소 요청 시 서버로 전달될지 지정
                c.setPath("/");		// "/" 이하 모든 요청에 쿠키가 포함됨

                /* 쿠키의 수명 지정 */

                // Calendar 객체 : 시간을 저장하는 객체
                // Calendar.getInstance() : 현재 시간이 저장된 객체가 반환됨
                Calendar cal = Calendar.getInstance();

                cal.add(cal.DATE, 1);	// 1일 더하기 = 24*60*60초 더하기

                // 날짜 데이터를 지정된 포멧의 문자열로 변경하는 객체
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                // import java.util.Date
                Date currentDay = new Date();	// 현재 시간 (2024-10-08 10:14:30)

                // Calendar 객체 ==> Date 타입으로 변환
                Date b = new Date(cal.getTimeInMillis());

                // sdf.format(b) == "2024-10-09"
                // sdf.parse("2024-10-09") == 2024-10-09 00:00:00 Date 변환
                Date nextDay = sdf.parse(sdf.format(b));

                // 다음날 자정 - 현재 시간의 결과를 초(second) 단위로 얻어오기
                // 다음날 0시 0분 0초 까지 몇 초 남았나 계산
                long diff = (nextDay.getTime() - currentDay.getTime()) / 1000;

                // 쿠키 수명 설정
                c.setMaxAge((int)diff);

                // 응답 객체에 쿠키를 추가해서
                // 응답 시 클라이언트에게 전달할 수 있게 하기
                resp.addCookie(c);
            }
        }


        /* ---------- 조회 수 증가 끝   ---------- */

        model.addAttribute("flea", flea);

        // 조회된 이미지 목록이 있을 경우
        if(flea.getImageList().isEmpty() == false) {
            
            // for 문 시작 인덱스 지정
            int start = 0;
            
            // 첫번째 이미지가 썸네일

            model.addAttribute("start", start);		// 0 또는 1
        }

        return "flea/fleaDetail";
    }

    
    /**
     * 현재 게시글이 포함된 목록의 페이지로 리다이렉트
     * @param boardNo : 게시글번호
     * @param paramMap : 요청 파라미터가 모두 담긴 Map
     * @throws UnsupportedEncodingException
     */
    
    @GetMapping("{boardNo:[0-9]+}/goToList")
    public String goToList(
            @PathVariable("boardNo") int boardNo,
            @RequestParam Map<String, Object> paramMap
    ) throws UnsupportedEncodingException {
        // paramMap 에 boardNo 추가
        paramMap.put("boardNo", boardNo);

        // 현재 게시글이 속해있는 페이지 번호 조회하는 서비스
        int cp = service.getCurrentPage(paramMap);

        // 목록 조회 리다이렉트
        String url = "redirect:/flea?cp=" + cp;

        // 검색인 경우 쿼리스트링 추가
        if(paramMap.get("key") != null) {

            // URLEncoder.encode("문자열", "UTF-8")
            // - UTF-8 형태의 "문자열"을
            //   URL이 인식할 수 있는 형태(application/x-www-from-urlencoded)로 변환
            String query = URLEncoder.encode(paramMap.get("query").toString(), "UTF-8");
            
            url += "&key=" + paramMap.get("key") + "&query=" + query;
        }
//        http://localhost/flea/1011/goToList?limit=10
        return url;
    }

    // @ExceptionHandler(예외클래스.class)
    // ==> 해당 예외 발생 시 아래 작성된 메서드가 수행되게 하는 어노테이션

    // - Class 레벨 : 클래스에서 발생하는 예외를 다 잡아서 처리
    // ==> 동작하려는 Controller 클래스에 작성

    // - Global 레벨 : 프로젝트 전체에서 발생하는 예외를 잡아서 처리
    // ==> @ControllerAdvice가 작성된 클래스에 작성

    /**
     * FleaController 에서 발생하는 예외를 한 번에 잡아서 처리하는 메서드
     */
    //@ExceptionHandler(Exception.class)
    public String fleaExceptionHandler(Exception e, Model model) {

        model.addAttribute("e", e);
        model.addAttribute("errorMessage", "게시글 관련 오류 발생");

        return "error/500";
    }
}
