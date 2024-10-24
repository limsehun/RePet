package edu.kh.repet.mypage.service;

import java.util.List;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.member.dto.Member;

public interface MyPageService {

	// 게시글 좋아요 조회
	List<Board> likeList(int memberNo);

	// 프로필 회원 조회
	Member memberList(int memberNo);

	List<Board> selectLikeList(int memberNo);

}
