package edu.kh.repet.sse.service;

import java.util.List;
import java.util.Map;

import edu.kh.repet.sse.dto.Notification;

public interface SseService {

	Map<String, Object> insertNotification(Notification notification);

	List<Notification> selectNotificationList(int memberNo);

	int notReadCheck(int memberNo);

	void deleteNotification(int notificationNo);

	void updateNotification(int notificationNo);

}
