package edu.kh.repet.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.member.dto.Member;

@Mapper
public interface MyPageMapper {


	
	// 프로필 회원 조회
	Member memberList(int memberNo);


	// 좋아요 한 게시물 리스트
	List<Board> selectLikeList(@Param("memberNo")int memberNo, RowBounds rowBounds, @Param("boardCode")int boardCode);


	// 좋아요 한 게시물 수
	int likeCount(int memberNo);


	// 회원 수정
	int updateMemberInfo(@Param("memberNo")int memberNo, @Param("encPw")String encPw, @Param("memberNickname")String memberNickname, @Param("profilePath") String profilePath);


	// 비밀 번호 검사
	String checkPw(int memberNo);


	// 닉네임 중복 검사
	int nicknameCheck(String nickname);
	
	
	// 회원 탈퇴
	int deleteUser(int memberNo);

}
