package edu.kh.repet.manager.service;

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

}
