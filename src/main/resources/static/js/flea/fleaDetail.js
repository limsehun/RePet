// 현재 상세 조회한 게시글 번호
const boardNo = location.pathname.split("/")[2];

// ----------------------------------------------------------------------------------------------------

/*
  * 삭제 버튼 클릭 시 *
  - 삭제 버튼 클릭 시 "정말 삭제 하시겠습니까?" confirm()

  - /editBoard/delete, POST 방식, 동기식 요청
    ==> form 태그 생성 + 게시글 번호가 세팅된 input
    ==> body 태그 제일 아래 추가해서 submit()

  - 서버(Java - 백엔드) 에서 로그인한 회원의 회원 번호를 얻어와
    로그인한 회원이 작성한 글이 맞는지 SQL에서 검사
*/

const deleteBtn = document.querySelector("#deleteBtn");

// 이벤트 리스너 ==> 특정 행동 감지기
// 삭제 버튼이 존재할 때 이벤트 리스너 추가
deleteBtn?.addEventListener("click", () => {

  if(confirm("정말 삭제 하시겠습니까?") == false) {
    return;
  }

  // 요청 주소
  const url = "/editFlea/delete";
  // 게시글 번호 == 전역 변수 boardNo

  // form 태그 생성
  const form = document.createElement("form");
  form.action = url;
  form.method = "POST";

  // input 태그 생성
  const input = document.createElement("input");
  input.type = "hidden";
  input.name = "boardNo";
  input.value = boardNo;

  form.append(input); // form 자식으로 input 추가

  document.querySelector("body").append(form);

  form.submit();  // 제출

});

// ----------------------------------------------------------------------------------------------------

/* 
  * 수정 버튼 클릭 시 *
  - /editBoard/{boardCode}/{boardNo}, POST, 동기식
  ==> form 태그 생성
  ==> body 태그 제일 아래 추가해서 submit()

  - 서버(Java) 에서 로그인한 회원의 회원 번호를 얻어와
    로그인한 회원이 작성한 글이 맞을 경우
    해당 글을 상세 조회 한 후
    수정 화면으로 전환
*/

const updateBtn = document.querySelector("#updateBtn");

updateBtn?.addEventListener("click", () => {
  
  const form = document.createElement("form");

  //    /editBoard/{boardCode}/{boardNo}/update
  form.action = location.pathname.replace("flea", "editFlea") + "/updateView";
  form.method = "POST";

  document.querySelector("body").append(form);
  form.submit();
});

// ----------------------------------------------------------------------------------------------------

/* 목록으로 버튼 클릭 시 */
const goToListBtn = document.querySelector("#goToListBtn");

goToListBtn.addEventListener("click", () => {

  // 페이지 당 게시글 수
  const limit = 10;

  // location.href = location.pathname + "/goToList" + location.search + "?limit=" + limit;
  let url = location.pathname + "/goToList?limit=" + limit;

  // location.search : 쿼리스트링 반환
  // URLSearchParams 객체 : 쿼리스트링 관리하는 객체
  const params = new URLSearchParams(location.search);

  if(params.get("key") !== null) {
    // `` : String template 문자열을 만드는 기본 양식 제공해주는 기능
    url += `&key=${params.get("key")}&query=${params.get("query")}`;
  }

  location.href = url;

});



