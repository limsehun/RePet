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

		// 이메일(id)이 일치하는 회원 정보가 없을 경우
		if (loginMember == null)
			return null;

		if (!encoder.matches(password, loginMember.getPassword())) {
			return null;
		}

		// 4. 로그인 결과 반환
		return loginMember;
	}

	@Override
	public int signUp(Member inputMember) {
		// 1) 비밀번호 암호화(BCrypt)
//		String encPw = encoder.encode(inputMember.getPassword()); // 암호화된 비밀번호를
//		inputMember.setPassword(encPw); // inputMember에 set

		// 2) 주소 미입력(",,")시 null로 변경
		// text 타입의 input은 값이 작성이 안되면 "" (빈칸)
		// checkbox, radio가 체크가 안되면 null
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
