package edu.kh.repet.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.member.dto.Member;
import edu.kh.repet.mypage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
	
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
		Pagination pagination = new Pagination(cp, likeCount, 5, 5);
		
		int offset = (cp - 1) * pagination.getLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		List<Board> likeList = mapper.selectLikeList(memberNo, rowBounds);
		
		Member memberList = mapper.memberList(memberNo);
		
		Map<String, Object> map = Map.of("likeList", likeList, "pagination", pagination,  "memberList", memberList);
		
		return map;
	}

}
