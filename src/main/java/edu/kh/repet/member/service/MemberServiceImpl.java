package edu.kh.repet.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.member.mapper.MemberMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;

	
	private final BCryptPasswordEncoder encoder;
	


	@Override
	public Member login(String memberEmail, String memberPw) {
		Member loginMember = mapper.login(memberEmail);

		if (loginMember == null) return null;

		if (!encoder.matches(memberPw, loginMember.getMemberPw())) {
			return null;
		}

		return loginMember;
	}

	@Override
	public int signUp(Member inputMember) {

		String encPw = encoder.encode(inputMember.getMemberPw()); 
		inputMember.setMemberPw(encPw);

		if (inputMember.getMemberAddress().equals(",,")) {
			inputMember.setMemberAddress(null);
		}

		return mapper.signUp(inputMember);
	}

	@Override
	public int emailCheck(String memberEmail) {
		return mapper.emailCheck(memberEmail);
	}
	
	@Override
	public int pwCheck(String memberPw) {
		return mapper.pwCheck(memberPw);
	}

	@Override
	public int nicknameCheck(String memberNickname) {
		return mapper.nicknameCheck(memberNickname);
	}

}
