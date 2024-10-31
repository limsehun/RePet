/* 게시글 상세정보 모달 창 */
const modalBackground = document.querySelector("#modalBackground");
const closeModal = document.querySelector(".close-btn");
const deleteBtn = document.querySelector(".delete-btn");
const boardList = document.querySelector(".info-modal");

let cachedBoardList = [];

// 모달창 닫기
closeModal.addEventListener("click", () => {
  modalBackground.style.display = "none";
});



// 게시글 리스트 가져오기
const selectBoardList = (cp) => {
  fetch(`/manager/transaction/selectManagement?cp=${cp}`)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .then(map => {
      const list = map.boardList;
      const pagination = map.pagination;
      cachedBoardList = list; // 캐시 저장
      renderItems(list, cp, pagination.limit || 10);
      renderPagination(pagination);
    })
    .catch(error => console.error("에러 발생:", error));
}

// 상세 정보 모달 표시 함수
const showBoardDetails = (boardNo) => {
  const board = cachedBoardList.find(item => item.boardNo === boardNo);
  if (board) {
    document.querySelector(".board-title").innerText = board.boardTitle;
    document.querySelector(".board-nickname").innerText = board.memberNickname;
    document.querySelector(".board-write-date").innerText = board.boardWriteDate;
    // 추가 정보 표시
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = board.boardContent; // HTML 삽입
    document.querySelector(".board-content").innerText = tempDiv.textContent; // 내용 설정

    deleteBtn.addEventListener("click",() =>{
      if(confirm("정말 삭제 하시겠습니까?") == false){
        return;}

      fetch("/manager/transaction/deleteManagement?boardNo="+boardNo)
      .then(response=>{
        if(response.ok)return response.json();
        throw new Error("게시글 삭제 오류");
      })
      .then(result =>{
        if(result > 0){
          alert("삭제가 완료되었습니다.")
          modalBackground.style.display = "none";
          selectBoardList();
        } else {
          alert("삭제 실패");
        }
      })
      .catch(error => console.error("에러 발생:", error));
    });

    modalBackground.style.display = "flex"; // 모달 표시

  } else {
    console.error("해당 게시물의 정보를 찾을 수 없습니다.");
  }
}




// 항목 렌더링 함수
const renderItems = (list, currentPage, limit) => {
  boardList.innerHTML = ''; // 기존 내용 초기화
  const fragment = document.createDocumentFragment(); // Document Fragment 생성

  list.forEach((board, index) => {
    const tr = document.createElement("tr");

    const td1 = document.createElement("td");
    td1.innerText = ((currentPage - 1) * limit) + index + 1;

    const td2 = document.createElement("td");
    td2.innerText = board.boardTitle;

    const td3 = document.createElement("td");
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = board.boardContent;
    td3.textContent = tempDiv.textContent;

    const td4 = document.createElement("td");
    td4.innerText = board.boardWriteDate;

    const td5 = document.createElement("td");
    td5.innerText = board.memberNickname;

    const td6 = document.createElement("td");
    const detailBtn = document.createElement("button");
    detailBtn.innerText = "상세정보";
    detailBtn.onclick = () => showBoardDetails(board.boardNo);
    td6.append(detailBtn);

    tr.append(td1, td2, td3, td4, td5, td6);
    fragment.appendChild(tr); // Fragment에 추가
  });

  boardList.appendChild(fragment); // 한번에 DOM에 추가
}

// 페이지네이션 렌더링
const renderPagination = (pagination) => {
  const paginationBox = document.querySelector(".pagination");
  paginationBox.innerHTML = '';

  const createPageButton = (page, text, isActive = false) => {
    const button = document.createElement("a");
    button.href = "#";
    button.classList.add("page-btn");
    button.dataset.page = page;
    button.textContent = text;

    if (isActive) button.classList.add("active");

    button.addEventListener("click", (event) => {
      event.preventDefault();
      selectBoardList(page);
    });

    return button;
  };

  paginationBox.appendChild(createPageButton(1, "<<"));
  paginationBox.appendChild(createPageButton(pagination.prevPage, "<"));

  for (let i = pagination.startPage; i <= pagination.endPage; i++) {
    const isActive = i === pagination.currentPage;
    paginationBox.appendChild(createPageButton(i, i, isActive));
  }

  paginationBox.appendChild(createPageButton(pagination.nextPage, ">"));
  paginationBox.appendChild(createPageButton(pagination.maxPage, ">>"));
};



//--------------------------------------------


// DOMContentLoaded 이벤트
document.addEventListener("DOMContentLoaded", () => {
  selectBoardList(1);
  modalBackground.style.display = "none";
});
