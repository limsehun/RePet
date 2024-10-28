const connectSse = () => {

  /* 로그인이 되어있지 않은 경우 함수 종료 */
  if(notificationLoginCheck === false) return;

  console.log("connectSse() 호출")

  // 서버의 "/sse/connect" 주소로 연결 설정요청
  const eventSource = new EventSource("/sse/connect");


}


