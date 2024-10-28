package edu.kh.repet.sse.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.repet.sse.dto.Notification;

@Mapper
public interface SseMapper {

	int insertNotification(Notification notification);

	Map<String, Object> selectReceiveMember(int notificationNo);

	List<Notification> selectNotificationList(int memberNo);

	int notReadCheck(int memberNo);

	void deleteNotification(int notificationNo);

	void updateNotification(int notificationNo);

}
