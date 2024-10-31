package edu.kh.repet.manager.service;

import java.util.List;
import java.util.Map;

import edu.kh.repet.member.dto.Member;

public interface ManagerService {

	Map<String, Object> selectMemberList(int cp);

	int changeStatus(int memberNo);

	Map<String, Object> selectSearchList(int cp, Map<String, Object> paramMap);

}
