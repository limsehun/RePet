package edu.kh.repet.mypage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.member.dto.Member;
import edu.kh.repet.mypage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
	
	private final MyPageMapper mapper;

	// 게시글 리스트 조회
	@Override
	public List<Board> likeList(int memberNo) {
		return mapper.likeList(memberNo);
	}
	
	
	// 프로필 회원 조회
	@Override
	public Member memberList(int memberNo) {
		return mapper.memberList(memberNo);
	}
	
	@Override
	public List<Board> selectLikeList(int memberNo) {
		return mapper.selectLikeList(memberNo);
	}

}
