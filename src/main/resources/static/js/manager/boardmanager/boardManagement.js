/* 게시글 상세정보 모달 창 */
const modalBackground = document.querySelector("#modalBackground");

const closeModal = document.querySelector(".close-btn");

const deleteBtn = document.querySelector('.delete-btn');


// 모달창 닫기
closeModal.addEventListener("click",() => {

  modalBackground.style.display = "none";
});

/* 공통 데이터 요청 함수 */
const fetchData = (url, cp) => {
  return fetch(`${url}?cp=${cp}`)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .catch(error => {
      console.error("에러 발생:", error);
      throw error;
    });
};


let cachedBoardList = [];

const boardList = document.querySelector("#boardList");

const selectBoardList = (cp) => {
  fetch(`/manager/board/selectBoardList?cp=${cp}`)
  .then(response => {
    // 응답 성공 시 JSON형태의 응답 데이터를 JS 객체로 변경 
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(map => {

    const list = map.boardList;
    const pagination = map.pagination;
    const limit = pagination.limit || 10;

    // 리스트 데이터를 전역 변수에 캐시
    cachedBoardList = list;
    
    renderItems(list, cp, limit);
    renderPagination(pagination);
  })
  .catch(error => console.error("에러 발생:", error));
}

let currentBoardNo;

// 상세 정보 모달 표시 함수
const showBoardDetails = (boardNo) => {

  currentBoardNo = boardNo;

  // 캐시된 리스트 데이터에서 해당 boardNo를 가진 항목 찾기
  const board = cachedBoardList.find(item => item.boardNo === boardNo);

  console.log(board);
  
  if (board) {

    // 태그가 포함된 내용을 제거하고 순수 텍스트만 얻기 위해 임시 div 사용
    const tempDiv = document.createElement("div");

    // 모달에 데이터 표시
    document.querySelector(".board-title").innerText = board.boardTitle;

    // 내용에 포함된 태그를 제거한 순수 텍스트 추출
    tempDiv.innerHTML = board.boardContent; // HTML 삽입
    
    document.querySelector(".board-content").innerText = tempDiv.textContent; // 텍스트만 추출

    document.querySelector(".board-writer").innerText = board.memberNickname;

    document.querySelector(".board-date").innerText = board.boardWriteDate;

    // document.querySelector(".board-image").innerText = board.imageName;


    // 모달을 화면에 표시
    modalBackground.style.display = "flex";
  } else {
    console.error("해당 게시물의 정보를 찾을 수 없습니다.");
  }
};


// 삭제 버튼 클릭 이벤트 리스너
deleteBtn?.addEventListener("click", () => {
  if (confirm("정말 삭제 하시겠습니까?") == false) {
    return;
  }

  const url = "/manager/board/delete";
  
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(currentBoardNo)
  })
  .then(response => response.json())

  .then(result => {
    if (result > 0) {
      alert("삭제 되었습니다.");
      modalBackground.style.display = "none"; // 모달창 닫기
      selectBoardList(1); // 목록 새로고침
    } else {
      alert("삭제 실패: " + result.message); // 실패 메시지 출력
    }
  })
  .catch(error => {
    console.error("삭제 요청 중 오류:", error);
    alert("삭제 중 오류가 발생했습니다.");
  });
});




// 항목 렌더링 함수
const renderItems = (list, currentPage, limit) => {
  // 기존 tbody의 내용을 지우기
  boardList.innerHTML = ''; // 기존 항목 초기화

  // 서버에서 받아온 리스트를 하나씩 순회하며 테이블의 row로 추가
  list.forEach((board, index) => {
    // tr 요소 만들기
    const tr = document.createElement("tr");

    // 각 열(td) 요소를 생성해서 게시글 정보를 넣기
    const td1 = document.createElement("td");
    td1.innerText = ((currentPage - 1) * limit) + index + 1;

    const td2 = document.createElement("td");
    const titleLink = document.createElement("a"); // 제목에 사용할 링크 요소 생성
    titleLink.href = `/board/2/${board.boardNo}`; // 링크 기본 설정
    titleLink.innerText = board.boardTitle; // 제목 텍스트 설정

     // 1. 썸머노트 내용이라 태그까지 받아와짐 
    const td3 = document.createElement("td");
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = board.boardContent; // HTML을 div에 넣음
    
    // 2. 이미지 태그 제거
    const images = tempDiv.querySelectorAll('img');
    images.forEach(img => img.remove());
    
    // 3. 순수 텍스트만 가져오기
    let textContent = tempDiv.textContent;
    textContent = textContent.length > 10 ? textContent.substring(0, 10) + '...' : textContent;
    
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
      showBoardDetails(board.boardNo); // boardNo를 인자로 전달하여 해당 게시물 상세정보 표시
    };


    // 'clickable-link' 클래스를 추가
    titleLink.classList.add("clickable-link");
    td2.appendChild(titleLink); // << 추가 부분

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




