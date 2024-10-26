package edu.kh.repet.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.BoardImg;
import edu.kh.repet.board.service.EditBoardService;
import edu.kh.repet.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("editBoard")
@RequiredArgsConstructor
public class EditBoardController {

	private final EditBoardService service;
	
	@GetMapping("{boardCode}/insert")
	public String boardInsert(
			) {
		
		return "board/boardWrite";
	}
	
	@ResponseBody
	@PostMapping("uploadImages")
	public Map<String, List<String>> uploadImages(
			@RequestParam("files") MultipartFile[] files,
			@RequestParam("boardNo") int boardNo,
			@SessionAttribute("loginMember") Member loginMember
			){
		
		// 로그인 체크
		if(loginMember == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다");
		}
		
		List<BoardImg> boardImgs = service.saveImages(files, boardNo);
		// boardImgs 리스트에 담긴 각 BoardImg 객체에서 이미지 경로만
		// 추출한 문자열 리스트 생성
		List<String> urls = boardImgs.stream().map(BoardImg::getImgPath).collect(Collectors.toList());
		
		Map<String, List<String>> response = new HashMap<>();
		response.put("urls", urls);
		
		
		
		return response;
	}
	
	@ResponseBody
	@PostMapping("register")
	public Map<String, Boolean> register(
			@RequestBody Board board,
			@SessionAttribute("loginMember") Member loginMember
			){
		
		// 등록 정보에 로그인한 멤버 번호 설정
		board.setMemberNo(loginMember.getMemberNo());
		
		boolean success = service.register(board);
		
		return null;
	}
























}
