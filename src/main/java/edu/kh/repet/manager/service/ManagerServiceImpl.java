package edu.kh.repet.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.manager.mapper.ManagerMapper;
import edu.kh.repet.member.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{
	
	private final ManagerMapper mapper;
	
	@Override
	public Map<String, Object> selectMemberList(int cp) {
		
		int listCount = mapper.selectMemberCount();
		
		Pagination pagination = new Pagination(cp, listCount, 10, 5);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds bounds = new RowBounds(offset, limit);
		
		List<Member> memberList = mapper.selectMemberList(bounds);
		
		Map<String, Object> map 
		= Map.of("pagination", pagination, "memberList", memberList);
		
		return map;
	}
	
	@Override
	public int changeStatus(int memberNo) {
		return mapper.changeStatus(memberNo);
	}

	
	
	@Override
	public Map<String, Object> selectSearchList(int cp, Map<String, Object> paramMap) {
		
		int searchCount = mapper.getSearchCount(paramMap);
		
		// 2. Pagination 객체 생성 하기
		Pagination pagination = new Pagination(cp, searchCount);
		
		
		// 3. DB에서 cp(조회 하려는 페이지)에 해당하는 행을 조회
		int limit = pagination.getLimit(); // 10
		int offset = (cp - 1) * limit; // 이전 번호를 건너뜀
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		// 4. 검색 결과 + Pagenation 객체를 Map으로 묶어서 반환
		List<Member> memberList = mapper.selectSearchList(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberList", memberList);
		map.put("pagination", pagination);
		
		
		return map;
	}
	
	
	
}
