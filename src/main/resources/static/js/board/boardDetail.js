// 신고하기 버튼을 가져옴
const reportBtn = document.querySelector("#reportBtn");

// 신고하기 버튼 클릭 시 팝업 창 열기
reportBtn.addEventListener('click', function() {
    if (!loginCheck) {
        alert("로그인 후 이용해 주세요.");
        return; // 로그인하지 않았으면 신고 팝업을 띄우지 않음
    }

    // boardNo를 URL 파라미터로 추가해서 팝업 창으로 전달
    window.open(
        `/reportBoard/reportPopup?boardNo=${boardNo}`, // URL에 올바른 boardNo 추가
        '신고하기',          // 팝업 창 이름
        'width=500,height=600' // 창 크기 및 옵션
    );
});


const boardNo = location.pathname.split("/")[3];


// 좋아요 버튼 클릭
const boardLike = document.querySelector("#boardLike");
boardLike.addEventListener("click", e => {

    // 로그인 여부
    if(loginCheck === false) {
        alert("로그인 후 이용해주세요");
        return;
    }

    // 비동기로 좋아요 요청
    fetch("/board/like", {
        method: "POST",
        headers: {"Content-Type" : "application/json"},
        body: boardNo
    })
    .then(response => {
        if(response.ok) return response.json();
        throw new Error("좋아요 실패");
    })
    .then(result => {
        //console.log("result : ", result);
        
        if(result.check === 'insert') {
            boardLike.classList.add("fa-heart", "fa-solid");
            boardLike.classList.remove("fa-heart", "fa-regular");


    // ----------알림 보내기 추가------------------

     // 게시글 작성자에게 알림 보내기
      const content
      = `<strong>${memberNickname}</strong> 님이 
      <strong>${boardDetail.boardTitle}</strong> 게시글을 좋아합니다`;
      
      console.log("// 게시글 작성자에게 알림 보내기");
      sendNotification(
        "boardLike",
        location.pathname, // 게시글 상세 조회 페이지 주소
        boardDetail.boardNo,
        content
      );

      // ----------------------------
            boardLike.classList.add("fa-solid");
            boardLike.classList.remove("fa-regular");

        
        } else {
            boardLike.classList.add("fa-regular");
            boardLike.classList.remove("fa-solid");
        }

        boardLike.nextElementSibling.innerText = result.count;
    })
    .catch(err => console.error(err));
})

const deleteBtn = document.querySelector("#deleteBtn");

// 삭제 버튼이 존재할 때 이벤트 리스너 추가
deleteBtn?.addEventListener("click", () => {

  if(confirm("정말 삭제 하시겠습니까?") == false){
    return;
  }

  const url = "/editBoard/delete"; // 요청 주소
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

  // body 자식으로 form 추가
  document.querySelector("body").append(form); 

  form.submit(); // 제출
});


/* 수정 */
const updateBtn = document.querySelector("#updateBtn");

updateBtn?.addEventListener("click", () => {

    if(loginCheck === false) {
        alert("로그인 후 이용할 수 있습니다")
        return;
    }

  const form = document.createElement("form");
  location.pathname = "/editBoard/{boardCode}/{boardNo}";
  form.action = location.pathname.replace("board", "editBoard") + "/boardModifyView";
  form.method = "POST";

  document.querySelector("body").append(form);
  form.submit();

});

const goToListBtn = document.querySelector("#goToListBtn");

goToListBtn.addEventListener("click", () => {

  // 페이징당 게시글 수
  const limit = 10;

  location.pathname = "/editBoard/{boardCode}/{boardNo}";
  let url = location.pathname + "/goToList?limit=" + limit;
  // /board/{boardCode}/{boardNo}/goToLis?key=&query=검색어&limit=10

  // location.search : 쿼리 스트링 반환
  // URLSearchParams 객체 : 쿼리스트링 관리하는 객체 
  const params = new URLSearchParams(location.search);

  

  location.href = url;
  
});
