package edu.kh.repet.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.member.dto.Member;

@Mapper
public interface MyPageMapper {


	
	// 프로필 회원 조회
	Member memberList(int memberNo);


	// 좋아요 한 게시물 리스트
	List<Board> selectLikeList(int memberNo);


	// 좋아요 한 게시물 수
	int likeCount(int memberNo);

}
