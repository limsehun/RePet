package edu.kh.repet.sse.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.repet.sse.dto.Notification;
import edu.kh.repet.sse.mapper.SseMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SseServiceImpl implements SseService {

	private final SseMapper mapper;

	@Override
	public Map<String, Object> insertNotification(Notification notification) {

		// 결과 저장용 map
		Map<String, Object> map = new HashMap<>();

		// 알림 삽입
		int result = mapper.insertNotification(notification);

		if (result > 0) {

			// 알림을 받아야하는 회원의 번호 + 안읽은 알람 개수 조회
      map = mapper.selectReceiveMember(notification.getNotificationNo());
		}

		return map;
	}

	// 로그인한 회원의 알림 목록 조회
	@Override
	public List<Notification> selectNotificationList(int memberNo) {
		return mapper.selectNotificationList(memberNo);
	}

	@Override
	public int notReadCheck(int memberNo) {
		return mapper.notReadCheck(memberNo);
	}

	@Override
	public void deleteNotification(int notificationNo) {
		mapper.deleteNotification(notificationNo);
	}
	
	@Override
	public void updateNotification(int notificationNo) {
		mapper.updateNotification(notificationNo);
	}

}
