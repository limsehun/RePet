const connectSse = () => {

  /* 로그인이 되어있지 않은 경우 함수 종료 */
  if (loginCheck === false) return;

  console.log("connectSse() 호출")

  // 서버의 "/sse/connect" 주소로 연결 요청
  const eventSource = new EventSource("/sse/connect");

  // -------------------------------------------------------

  /* 서버로 부터 메시지가 왔을 경우(전달 받은 경우) */
  eventSource.addEventListener("message", e => {
    console.log(e.data);

    const obj = JSON.parse(e.data);
    console.log(obj);

    // 종 버튼에 색 추가(활성화)
    const notificationBtn = document.querySelector(".notification-btn");

    notificationBtn.classList.add("fa-solid");
    notificationBtn.classList.remove("fa-regular");

    // 알림 개수 표시
    const notificationCountArea
      = document.querySelector(".notification-count-area");

    notificationCountArea.innerText = obj.likeCount;

    /* 만약 알림 목록이 열려 있을 경우 */
    const notificationList
      = document.querySelector(".notification-list");

    if (notificationList.classList.contains("notification-show")) {
      selectNotificationList(); // 알림 목록 비동기 조회
    }
  });


  eventSource.addEventListener("error", () => {
    console.log("SSE 재연결 시도")
    eventSource.close(); // 기존 연결 닫기

    // 5초 후 재연결 시도
    setTimeout(() => connectSse(), 5000);
  })
}

const sendNotification = (type, url, pkNo, content) => {
  console.log("sse.sendNotification 실행 " +type );
  if (loginCheck === false) return;

  const notification = {
    "notificationType": type,
    "notificationUrl": url,
    "pkNo": pkNo,
    "notificationContent": content
  }

  fetch("/sse/send", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(notification)
  })
    .then(response => {
      if (!response.ok) { // 비동기 통신 실패
        throw new Error("전송 실패");
      }
      console.log("전송 성공");
    })
    .catch(err => console.error(err));
}

const selectNotificationList = () => {

  if (loginCheck === false) return;

  fetch("/notification")
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("목록 조회 실패");
    })
    .then(alarmList => {
      const notiList = document.querySelector(".notification-list");
      notiList.innerHTML = '';

      for (let alarm of alarmList) {

        const notiItem = document.createElement("li");
        notiItem.className = 'notification-item';

        if (alarm.notificationCheck == 'N')
          notiItem.classList.add("not-read");

        const notiText = document.createElement("div");
        notiText.className = 'notification-text';

        notiText.addEventListener("click", e => {

          // 만약 읽지 않은 알람인 경우
          if (alarm.notificationCheck == 'N') {
            fetch("/notification", {
              method: "PUT",
              headers: { "Content-Type": "application/json" },
              body: alarm.notificationNo
            })

          }


          location.href = alarm.notificationUrl;

        })
        const senderProfile = document.createElement("img");
        if (alarm.sendMemberProfileImg == null) senderProfile.src = notificationDefaultImage;  // 기본 이미지
        else senderProfile.src = alarm.sendMemberProfileImg; // 프로필 이미지

        // 알림 내용 영역
        const contentContainer = document.createElement("div");
        contentContainer.className = 'notification-content-container';

        // 알림 보내진 시간
        const notiDate = document.createElement("p");
        notiDate.className = 'notification-date';
        notiDate.innerText = alarm.notificationDate;

        // 알림 내용
        const notiContent = document.createElement("p");
        notiContent.className = 'notification-content';
        notiContent.innerHTML = alarm.notificationContent; // 태그가 해석 될 수 있도록 innerHTML

        // 삭제 버튼
        const notiDelete = document.createElement("span");
        notiDelete.className = 'notidication-delete';
        notiDelete.innerHTML = '&times;';

        notiDelete.addEventListener("click", e => {

          fetch("/notification", {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: alarm.notificationNo
          })
            .then(resp => {
              if (resp.ok) {
                // 클릭된 x버튼이 포함된 알림 삭제
                notiDelete.parentElement.remove();
                notReadCheck();
                return;
              }
              throw new Error("네트워크 응답이 좋지 않습니다.");
            })
            .catch(err => console.error(err));
        })

        // 조립
        notiList.append(notiItem);
        notiItem.append(notiText, notiDelete);
        notiText.append(senderProfile, contentContainer);
        contentContainer.append(notiDate, notiContent);
      }
    })
    .catch(err => console.error(err));
}


const notReadCheck = () => {

  // 로그인 되어있지 않으면 리턴
  if(!loginCheck) return;

  fetch("/notification/notReadCheck")
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("알림 개수 조회 실패");
  })
  .then(alarmcount => {

    const notificationBtn = 
        document.querySelector(".notification-btn");

    const notificationCountArea
        = document.querySelector(".notification-count-area");


    notificationCountArea.innerText = alarmcount;


    if(alarmcount > 0){
      notificationBtn.classList.add("yellow");
      notificationBtn.classList.remove("fa-regular");
    } else { // 모든 알림을 읽은 상태
      notificationBtn.classList.remove("yellow");
      notificationBtn.classList.add("regular");
    }
  })
  .catch(err => console.error(err));
}


document.addEventListener("DOMContentLoaded", () => {
  connectSse(); // SSE 연결

  notReadCheck(); // 알림 개수 조회

  // 종 버튼(알림) 클릭 시 알림 목록이 출력하기
  const notificationBtn
    = document.querySelector(".notification-btn");

  notificationBtn?.addEventListener("click", () => {

    // 알림 목록
    const notificationList
      = document.querySelector(".notification-list");

    // 알림 목록이 보이고 있을 경우
    if (notificationList.classList.contains("notification-show")) {

      // 안보이게 하기
      notificationList.classList.remove("notification-show");
    }

    else { // 안보이는 경우
      selectNotificationList(); // 비동기로 목록 조회 후

      // 화면에 목록 보이게 하기
      notificationList.classList.add("notification-show");
    }
  });


  const params = new URLSearchParams(location.search);
  const cn = params.get("cn"); // cn 값 얻어오기

  if(cn != null){ // cn이 존재하는 경우
    const targetId = "c" + cn; // "c100", "c1234" 형태로 변환

    // 아이디가 일치하는 댓글 요소 얻어오기
    const target = document.getElementById(targetId);

    // 댓글 요소가 제일 위에서 얼만큼 떨어져 있는지 반환 받기
    const position = target.offsetTop;

    // 창을 스크롤
    window.scrollTo({
      top : position - 75,
      behavior : "smooth" // 부드럽게 동작(행동)하도록 지정
    });

  }

});

//------------------------------------------------------------

document.addEventListener("DOMContentLoaded", () => {

  
  // 로그인 모달창 생성
  const login = document.querySelector("#login");
  const registerBtn = document.querySelector("#registerBtn");
  const lgModalBg = document.querySelector(".lgModal-bg");
  
  login.addEventListener("click", () => {
  
    lgModalBg.classList.remove("popup-hidden");
  
  })
  
  registerBtn.addEventListener("click", () => {
  
    pwModalBg.classList.remove("popup-hidden");
  
  })
  
  
  
  
  
  // 로그인 모달창 닫기 버튼
  document.querySelector("#close").addEventListener("click", () => {
  
    document.querySelector(".lgModal-bg")
      .classList.add("popup-hidden");
  });
  
  // 회원가입 모달창 닫기 버튼
  document.querySelector("#close2").addEventListener("click", () => {
  
    document.querySelector(".pwModal-bg")
      .classList.add("popup-hidden");
  });
  
  
  
  // 회원 가입 모달창 생성/ 로그인 모달창 제거
  const signUpBtn = document.querySelector("#signUpBtn");
  // const lgModalBg = document.querySelector(".lgModal-bg");
  const pwModalBg = document.querySelector(".pwModal-bg");
  
  signUpBtn.addEventListener("click", () => {
  
    lgModalBg.classList.add("popup-hidden");
    pwModalBg.classList.remove("popup-hidden");
  
  });
  
  const findPassword = document.querySelector("#find-password");
  const findPwModalBg = document.querySelector(".findPwModal-bg");
  
  findPassword.addEventListener("click", () => {
  
    lgModalBg.classList.add("popup-hidden");
    findPwModalBg.classList.remove("popup-hidden");
  
  });
  
  
  // 비밀번호 찾기 모달창 닫기 버튼
  document.querySelector("#close4").addEventListener("click", () => {
  
    document.querySelector(".findPwModal-bg")
      .classList.add("popup-hidden");
  });
  
  
  
  const refindLink = document.querySelector(".refind-link");
  const refindEmail = document.querySelector("#refind-email");
  
  const newPwMessage = document.querySelector("#newPwMessage");
  
  refindLink.addEventListener("click", () => {
  
    if(refindEmail.value.trim().length === 0){
      alert("유효한 이메일 작성 후 클릭 하세요");
      return;
    }
  
  
    fetch("/email/sendNewPw", {
      method  : "POST",
      headers : {"Content-Type" : "application/json"},
      body    : refindEmail.value
  
    })
    .then(response => {
      if(response.ok) return response.text();
      throw new Error("이메일 발송 실패");
    })
    .then(result => {
      // 백엔드 작성 후 나머지 코드 작성 예정
      console.log(result);
  
    })
    .catch(err => console.error(err));
  
    findPwModalBg.classList.add("popup-hidden");
    lgModalBg.classList.remove("popup-hidden");
    
    alert("새로운 비밀번호가 발송 되었습니다");
  })

})