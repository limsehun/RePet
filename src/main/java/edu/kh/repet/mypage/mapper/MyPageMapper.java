package edu.kh.repet.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.member.dto.Member;

@Mapper
public interface MyPageMapper {

	// 게시글 리스트 조회
	List<Board> likeList(int memberNo);

	
	// 프로필 회원 조회
	Member memberList(int memberNo);


	List<Board> selectLikeList(int memberNo);

}
