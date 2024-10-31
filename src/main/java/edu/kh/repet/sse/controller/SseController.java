package edu.kh.repet.sse.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import edu.kh.repet.member.dto.Member;
import edu.kh.repet.sse.dto.Notification;
import edu.kh.repet.sse.service.SseService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@ResponseBody
public class SseController {

	@Autowired
	private SseService service;

	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>(); // == 연결된 클라이언트 대기 명단

	@GetMapping("sse/connect")
	public SseEmitter sseConnect(@SessionAttribute("loginMember") Member loginMember) {

		String clientId = loginMember.getMemberNo() + "";
		SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);
		emitters.put(clientId, emitter);
		emitter.onCompletion(() -> emitters.remove(clientId));
		emitter.onTimeout(() -> emitters.remove(clientId));
		return emitter;
	}

	@PostMapping("sse/send")
	public void sendNotification(
			@RequestBody Notification notification,
			@SessionAttribute("loginMember") Member loginMember) {

		notification.setSendMemberNo(loginMember.getMemberNo());

		Map<String, Object> map = service.insertNotification(notification);

		// 알림 받을 회원 번호
		String clientId = map.get("receiveMemberNo").toString();

		SseEmitter emitter = emitters.get(clientId);

		// clientId가 일치하는 클라이언트가 있을 경우
		if (emitter != null) {
			try {
				emitter.send(map);
			} catch (Exception e) {
				emitters.remove(clientId);
			}
		}
	}
	
	
	
	@PostMapping("sse/warningAlarm")
	public void warningNotification(
			@RequestBody Notification notification,
			@SessionAttribute("loginMember") Member loginMember) {
		
		notification.setSendMemberNo(loginMember.getMemberNo());
		
		Map<String, Object> map = service.insertNotification(notification);
		
		// 알림 받을 회원 번호
		String clientId = map.get("receiveMemberNo").toString();
		
		SseEmitter emitter = emitters.get(clientId);
		
		// clientId가 일치하는 클라이언트가 있을 경우
		if (emitter != null) {
			try {
				emitter.send(map);
			} catch (Exception e) {
				emitters.remove(clientId);
			}
		}
	}
	
	
  @GetMapping("notification")
  public List<Notification> selectNotificationList(
    @SessionAttribute("loginMember") Member loginMember
    ){
  	
  	int memberNo = loginMember.getMemberNo();

  	return service.selectNotificationList(memberNo);
  }

  
  
  @GetMapping("notification/notReadCheck")
  public int notReadCheck(
  		@SessionAttribute("loginMember") Member loginMember) {
  	
  	int memberNo = loginMember.getMemberNo();
  	
  	return service.notReadCheck(memberNo);
  }
  
  /** 알림 삭제 
   * @param notificationNo
   * @param loginMember
   */
  @DeleteMapping("notification")
  public void deleteNotification(
  		@RequestBody int notificationNo,
  		@SessionAttribute("loginMember") Member loginMember) {

  	service.deleteNotification(notificationNo);
  }
  
  
	@PutMapping("notification")
	public void updateNotification(
			@RequestBody int notificationNo) {
		
		service.updateNotification(notificationNo);		
	}
	
	
	
	
}
