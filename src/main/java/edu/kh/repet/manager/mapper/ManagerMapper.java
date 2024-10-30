package edu.kh.repet.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.repet.member.dto.Member;

@Mapper
public interface ManagerMapper {

	List<Member> selectMemberList(RowBounds bounds);

	int selectMemberCount();

	int changeStatus(int memberNo);

	

}
