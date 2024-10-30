package edu.kh.repet.mypage.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.repet.member.dto.Member;


public interface MyPageService {

	// 프로필 회원 조회
	Map<String, Object> memberList(int memberNo);

	
	Map<String, Object> selectLikeList(int memberNo, int cp);

	// 회원 수정
	int updateMemberInfo(String memberPw, Member loginMember, String newPw, String memberNickname, MultipartFile profileImg);


	// 비밀번호 중복 검사
	int checkPw(String inputPw, int memberNo);


	// 닉네임 중복 검사
	int nicknameCheck(String nickname);

	// 회원 탈퇴
	int deletUser(int memberNo);
	

	// 게시물 리스트 조회
	Map<String, Object> selectBoardList(int memberNo, int cp);

	
	// 게시물 수 조회
	int boardCount(int memberNo);


	// 댓글 리스트 조회
	Map<String, Object> selectCommentList(int memberNo, int cp);

}
