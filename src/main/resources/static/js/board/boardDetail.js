// 신고하기 버튼을 가져옴
const reportBtn = document.querySelector("#reportBtn");
// 신고하기 버튼 클릭 시 팝업 창 열기
reportBtn.addEventListener('click', function() {
    // 새 창 열기 (팝업창 크기 지정)
    window.open(
        '/board/reportPopup', // 팝업 창에 로드할 페이지
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
        console.log("result : ", result);
        
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
        
        } else {
            boardLike.classList.add("fa-regular");
            boardLike.classList.remove("fa-solid");
        }

        boardLike.nextElementSibling.innerText = result.count;
    })
    .catch(err => console.error(err));
})

// 삭제 버튼 클릭 시
const deleteBtn = document.querySelector("#deleteBtn");

deleteBtn?.addEventListener("click", () => {

    if(confirm("정말 삭제 하시겠습니까?") == false ){
        return;
    } 

    console.url = "/editBoard/delete";

    const form = document.createElement("form");
    form.action = url;
    form.method = "POST";

})