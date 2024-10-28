package edu.kh.repet.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.member.dto.Member;
import edu.kh.repet.mypage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
	
	private final BCryptPasswordEncoder encoder;
	
	private final MyPageMapper mapper;
	
	@Override
	public Map<String, Object> memberList(int memberNo) {
		
		Member memberList = mapper.memberList(memberNo);
		
		int likeCount = mapper.likeCount(memberNo);
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberList", memberList);
		map.put("likeCount", likeCount);
		
		return map;
	}
	
	@Override
	public Map<String, Object> selectLikeList(int memberNo, int cp) {
		
		int likeCount = mapper.likeCount(memberNo);
		
		// 페이지 네이션 객체
		// cp 현재 페이지 번호
		// 한 페이지에 표실할 게시물 수
		// 링크 수
		Pagination pagination = new Pagination(cp, likeCount, 5, 5);
		
		// ex) 현재 페이지 2 - 1 = 1
		// 1 * 5 = 5
		// 해당 인덱스 부터 게시물 가져오는 값
		int offset = (cp - 1) * pagination.getLimit();
		
		//offset: 데이터베이스에서 데이터를 가져올 시작 위치.
		// pagination.getLimit(): 한 번에 가져올 게시물의 수.
		// RowBounds는 MyBatis가 SQL 쿼리를 실행할 때 자동으로 OFFSET과 LIMIT을 적용
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		int boardCode = 2;
		
		List<Board> likeList = mapper.selectLikeList(memberNo, rowBounds, boardCode);
		
		
		Map<String, Object> map = Map.of("likeList", likeList, "pagination", pagination);
		
		return map;
	}
	
	
	@Override
	public int updateMemberInfo(String memberPw, Member loginMember, String newPw, String memberNickname) {
		
		// 비밀번호가 일치하지 않으면
		if(!encoder.matches(memberPw, loginMember.getMemberPw())) {
			return 0;
		}
		
		// 2) 새 비밀번호 암호화
		String encPw = encoder.encode(newPw);
		
		loginMember.setMemberPw(encPw); // 세션에 저장된 회원 정보 중 PW 변경
		
		return mapper.updateMemberInfo(loginMember.getMemberNo(), encPw, memberNickname);
	}

}
