package edu.kh.repet.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Member login(String email, String password) {
		Member loginMember = mapper.login(email);
		
		// 2. 이메일(id)이 일치하는 회원 정보가 없을 경우
		if(loginMember == null) return null;
		
		
		// 3. DB에서 조회된 비밀번호와, 입력받은 비밀번호가 같은지 확인
//		log.debug("비밀번호 일치? : {}", 
//							encoder.matches(memberPw, loginMember.getMemberPw()));
		
		// 입력 받은 비밀 번호와 DB에서 조회된 비밀 번호가 일치하지 않을 ㄸ
		if( !encoder.matches(password, loginMember.getPassword()) ) {
				return null;
		}
		
		// 4. 로그인 결과 반환
		return loginMember;
	}
	
	
}
