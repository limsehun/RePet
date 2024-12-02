package edu.kh.repet.mypage.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.repet.board.dto.Board;
import edu.kh.repet.board.dto.Pagination;
import edu.kh.repet.common.exception.FileUploadFailException;
import edu.kh.repet.common.util.FileUtil;
import edu.kh.repet.member.dto.Member;
import edu.kh.repet.mypage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
@Slf4j
public class MyPageServiceImpl implements MyPageService {
	
	private final BCryptPasswordEncoder encoder;
	
	private final MyPageMapper mapper;
	
	
	@Value("${my.profile.web-path}")
	private String profileWebPath; // 웹 접근 경로
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // 이미지 저장 서버 경로
	
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
		// cp 현재 페이지 번호
		// 한 페이지에 표실할 게시물 수
		// 링크 수
		Pagination pagination = new Pagination(cp, likeCount, 5, 5);
		
		// ex) 현재 페이지 2 - 1 = 1
		// 1 * 5 = 5
		// 해당 인덱스 부터 게시물 가져오는 값
		int offset = (cp - 1) * pagination.getLimit();
		
		//offset: 데이터베이스에서 데이터를 가져올 시작 위치.
		// pagination.getLimit(): 한 번에 가져올 게시물의 수.
		// RowBounds는 MyBatis가 SQL 쿼리를 실행할 때 자동으로 OFFSET과 LIMIT을 적용
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		int boardCode = 2;
		
		List<Board> likeList = mapper.selectLikeList(memberNo, rowBounds, boardCode);
		
		
		Map<String, Object> map = Map.of("likeList", likeList, "pagination", pagination);
		
		return map;
	}
	
	
	@Override
	public int updateMemberInfo(String memberPw, Member loginMember, String newPw, String memberNickname, MultipartFile profileImg) {
		
	    // 비밀번호가 일치하지 않으면
	    if (!encoder.matches(memberPw, loginMember.getMemberPw())) {
	        return 0; // 비밀번호 불일치
	    }

	    // 1) 새 비밀번호 암호화
	    String encPw = encoder.encode(newPw);
	    loginMember.setMemberPw(encPw); // 세션에 저장된 회원 정보 중 PW 변경
	    
	    

	    // 2) 프로필 이미지 처리
	    String profilePath = null; // 기본적으로 null로 설정 (프로필 이미지가 없을 경우)

	    if (!profileImg.isEmpty()) { // 새로운 프로필 이미지가 있는 경우에만 처리
	        // 파일명 변경
	        String rename = FileUtil.rename(profileImg.getOriginalFilename());

	        // 경로 설정 (웹 경로 + 폴더 경로)
	        String url = rename;
	        String savePath = profileFolderPath + rename;

	        try {
	            // 프로필 폴더 생성 여부 확인
	            File folder = new File(profileFolderPath);
	            if (!folder.exists()) {
	                folder.mkdirs();
	            }

	            // 파일 저장
	            profileImg.transferTo(new File(savePath));
	            profilePath = url; // 성공적으로 저장된 경우, 프로필 경로 설정
	            
	            // 디버그 로그
	            System.out.println("프로필 이미지 경로: " + profilePath);

	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new FileUploadFailException("프로필 이미지 수정 실패");
	        }
	    }

	    // 3) DB 업데이트 준비
	    loginMember.setMemberNickname(memberNickname);
	    loginMember.setProfileImg(profilePath); // 세션에 이미지 경로 설정

	    // DB 업데이트 (프로필 이미지와 함께 정보 변경)
	    return mapper.updateMemberInfo(loginMember.getMemberNo(), encPw, memberNickname, profilePath);
	}
	
	
	//비밀번호 중복 검사
	@Override
	public int checkPw(String inputPw, int memberNo) {
		
		String memberPw = mapper.checkPw(memberNo);
		
		 if (encoder.matches(inputPw, memberPw)) {
			 return 1; // 일치할 경우
	   }
	
		return 0;
	}
	
	
	// 닉네임 중복 검사
	@Override
	public int nicknameCheck(String nickname) {
		return mapper.nicknameCheck(nickname);
	}
	
	
	// 회원탈퇴
	@Override
	public int deletUser(int memberNo) {
		return mapper.deleteUser(memberNo);
	}
	
	
	@Override
	public int boardCount(int memberNo) {
		return mapper.boardCount(memberNo);
	}
	
@	Override
	public int commentCount(int memberNo) {
		return mapper.commentCount(memberNo);
	}


	// 게시물 리스트 조회
	@Override
	public Map<String, Object> selectBoardList(int memberNo, int cp) {
		
		int boardListCount = mapper.boardCount(memberNo);
		
		log.debug("paramMap : {}", boardListCount);
		
		// 페이지 네이션 객체
		// cp 현재 페이지 번호
		// 한 페이지에 표실할 게시물 수
		// 링크 수
		Pagination pagination = new Pagination(cp, boardListCount, 5, 5);
		
		// ex) 현재 페이지 2 - 1 = 1
		// 1 * 5 = 5
		// 해당 인덱스 부터 게시물 가져오는 값
		int offset = (cp - 1) * pagination.getLimit();
		
		//offset: 데이터베이스에서 데이터를 가져올 시작 위치.
		// pagination.getLimit(): 한 번에 가져올 게시물의 수.
		// RowBounds는 MyBatis가 SQL 쿼리를 실행할 때 자동으로 OFFSET과 LIMIT을 적용
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		
		List<Board> boardList = mapper.selectBoardList(memberNo, rowBounds);
		
		
		
		Map<String, Object> map = Map.of("boardList", boardList, "pagination", pagination);
		
		return map;
	}
	
	
	
	@Override
	public Map<String, Object> selectCommentList(int memberNo, int cp) {
		
		int boardListCount = mapper.commentCount(memberNo);
		
		
		// 페이지 네이션 객체
		// cp 현재 페이지 번호
		// 한 페이지에 표실할 게시물 수
		// 링크 수
		Pagination pagination = new Pagination(cp, boardListCount, 5, 5);
		
		// ex) 현재 페이지 2 - 1 = 1
		// 1 * 5 = 5
		// 해당 인덱스 부터 게시물 가져오는 값
		int offset = (cp - 1) * pagination.getLimit();
		
		//offset: 데이터베이스에서 데이터를 가져올 시작 위치.
		// pagination.getLimit(): 한 번에 가져올 게시물의 수.
		// RowBounds는 MyBatis가 SQL 쿼리를 실행할 때 자동으로 OFFSET과 LIMIT을 적용
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		
		
		List<Board> commentList = mapper.selectCommentList(memberNo, rowBounds);
		
		
		Map<String, Object> map = Map.of("commentList", commentList, "pagination", pagination);
		
		return map;
	}
	
}
