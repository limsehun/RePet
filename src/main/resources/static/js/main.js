// 로그인 버튼에 이벤트 리스너 추가
document.getElementById('loginBtn').addEventListener('click', function() {
  // 팝업 창 옵션 설정
  const left = (window.innerWidth - width) / 2;
  const top = (window.innerHeight - height) / 2;
  
  // 팝업 창 열기
  const loginWindow = window.open(
      'login.html', 
      'Login', 
      `left=${left},
       top=${top},
       resizable=no,
       scrollbars=no`
  );
});