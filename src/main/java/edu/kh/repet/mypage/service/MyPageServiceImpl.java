package edu.kh.repet.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Board;
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
	public List<Board> selectLikeList(int memberNo) {
		return mapper.selectLikeList(memberNo);
	}

}
