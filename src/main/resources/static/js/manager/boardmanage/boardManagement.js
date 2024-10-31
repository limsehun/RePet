
const boardList = document.querySelector("#boardList");


const selectBoardList = (cp) => {
  fetch(`/manager/boardManager/selectBoardList?cp=${cp}`)
  .then(response => {
    // 응답 성공 시 JSON형태의 응답 데이터를 JS 객체로 변경 
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {
    console.log(list);
    renderItems(list);
  })
  .catch(error => console.error("에러 발생:", error));
}


// 항목 렌더링 함수
const renderItems = (list) => {
  // 기존 tbody의 내용을 지우기
  boardList.innerHTML = ''; // 기존 항목 초기화

  // 서버에서 받아온 리스트를 하나씩 순회하며 테이블의 row로 추가
  list.forEach((board, index) => {
    // tr 요소 만들기
    const tr = document.createElement("tr");

    // 각 열(td) 요소를 생성해서 게시글 정보를 넣기
    const td1 = document.createElement("td");
    td1.innerText = index + 1; // 순번 표시 (1부터 시작)

    const td2 = document.createElement("td");
    td2.innerText = board.boardTitle; // 제목

     // 1. 썸머노트 내용이라 태그까지 받아와짐 
    const td3 = document.createElement("td");
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = board.boardContent; // HTML을 div에 넣음
    
    // 2. 이미지 태그 제거
    const images = tempDiv.querySelectorAll('img');
    images.forEach(img => img.remove());
    
    // 3. 순수 텍스트만 가져오기
    const textContent = tempDiv.textContent;
    
    // 4. td 요소에 텍스트 내용 설정
    td3.textContent = textContent; // 태그가 없는 순수한 텍스트만 설정

    const td4 = document.createElement("td");
    td4.innerText = board.boardWriteDate; // 작성일

    const td5 = document.createElement("td");
    td5.innerText = board.memberNickname; // 작성자 (닉네임)

    const td6 = document.createElement("td");
    const detailBtn = document.createElement("button");
    detailBtn.innerText = "상세정보";
    detailBtn.classList.add("detail-btn");
    detailBtn.onclick = () => {
      viewBoardDetails(board.boardNo);
    };
    td6.append(detailBtn); // 관리 버튼 추가

    // 만들어진 td들을 tr에 추가
    tr.append(td1, td2, td3, td4, td5, td6);

    // tr을 tbody(#boardList)에 추가
    boardList.appendChild(tr);
  });
}

const paginationBox = document.querySelector(".pagination");

const renderPagination = (pagination) => {

  // let paginationBox;

  paginationBox.innerHTML = '';  // 기존 페이지 버튼 초기화

  const createPageButton = (page, text, isActive = false) => {
    const button = document.createElement("a");
    button.href = "#";
    button.classList.add("page-btn");
    button.dataset.page = page;
    button.textContent = text;

    if (isActive) button.classList.add("active");

    button.addEventListener("click", (event) => {
      event.preventDefault();
      const cp = parseInt(event.target.dataset.page);

      // 모든 페이지 버튼에서 active 클래스를 제거
      document.querySelectorAll(".page-btn").forEach(btn => btn.classList.remove("active"));
      
      // 현재 클릭된 버튼에 active 클래스 추가
      button.classList.add("active");

      selectBoardList(cp);
    });

    return button;
  };

  // <<, < 버튼 추가
  paginationBox.appendChild(createPageButton(1, "<<"));
  paginationBox.appendChild(createPageButton(pagination.prevPage, "<"));

  // 동적 페이지 번호 버튼 생성
  for (let i = pagination.startPage; i <= pagination.endPage; i++) {
    const isActive = i === pagination.currentPage;
    paginationBox.appendChild(createPageButton(i, i, isActive));
  }

  // >, >> 버튼 추가
  paginationBox.appendChild(createPageButton(pagination.nextPage, ">"));
  paginationBox.appendChild(createPageButton(pagination.maxPage, ">>"));
};


// DOMContentLoaded 이벤트
document.addEventListener("DOMContentLoaded", () => {
    selectBoardList(1);
});