package edu.kh.repet.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;

	private BCryptPasswordEncoder encoder;

	@Override
	public Member login(String email, String password) {
		Member loginMember = mapper.login(email);

		if (loginMember == null)
			return null;

//		if (!encoder.matches(password, loginMember.getPassword())) {
//			return null;
//		}

		return loginMember;
	}

	@Override
	public int signUp(Member inputMember) {
		// 비밀번호 암호화(BCrypt)
//		String encPw = encoder.encode(inputMember.getPassword()); // 암호화된 비밀번호를
//		inputMember.setPassword(encPw); // inputMember에 set

		// 주소 미입력(",,")시 null로 변경
		if (inputMember.getMemberAddress().equals(",,")) {
			inputMember.setMemberAddress(null);
		}

		// 3) mapper 호출 후 결과 반환
		return mapper.signUp(inputMember);
	}

	@Override
	public int emailCheck(String email) {
		return mapper.emailCheck(email);
	}

	@Override
	public int nicknameCheck(String nickname) {
		// TODO Auto-generated method stub
		return mapper.nicknameCheck(nickname);
	}

}
