/* 게시글 상세정보 모달 창 */
const modalBackground = document.querySelector("#modalBackground");
const closeModal = document.querySelector(".close-btn");

const deleteReport = document.querySelector(".delete-btn");
const boardListElement  = document.querySelector(".info-modal");

let cachedBoardList = [];
let reportBoardList = [];

// 모달창 닫기
closeModal.addEventListener("click", () => {
  modalBackground.style.display = "none";
});
deleteReport.addEventListener("click", () => {
  modalBackground.style.display = "none";
});


// 게시글 리스트 가져오기
// 게시글 리스트 가져오기
const selectReportBoardList = (cp) => {
  fetch(`/manager/transaction/selectReport?cp=${cp}`)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .then(map => {

      
      const list = map.boardList;
      const pagination = map.pagination;
      const reportCount = map.reportCount;
      const reportBoardList2 = map.reportBoardList;

      cachedBoardList = list; // 캐시 저장
      reportBoardList = reportBoardList2; // 캐시 저장
 
      console.log(map);
      console.log(reportCount);
      console.log(reportBoardList2);
      console.log(cachedBoardList);

      renderItems(cachedBoardList, reportBoardList2, reportCount, cp, pagination.limit || 10);
      renderPagination(pagination);
    })
    .catch(error => console.error("에러 발생:", error));
}

let alertFlag = false;


// 상세 정보 모달 표시 함수
const showBoardDetails = (boardNo) => {
  const board = cachedBoardList.find(item => item.boardNo === boardNo);
  const report = reportBoardList.filter(item => item.boardNo === boardNo);
  console.log(board);
  console.log(report);
  if (board) {
    document.querySelector(".board-title").innerText = board.boardTitle;
    document.querySelector(".board-nickname").innerText = board.memberNickname;
    document.querySelector(".board-write-date").innerText = board.boardWriteDate;
    // 추가 정보 표시
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = board.boardContent; // HTML 삽입
    document.querySelector(".board-content").innerText = tempDiv.textContent; // 내용 설정

     // 삭제 핸들러
    const deleteHandler = () => {
      alertFlag = true;
      
      fetch(`/manager/transaction/deleteReportBord?boardNo=${boardNo}`)
        .then(response => {
          if (!response.ok) throw new Error("게시글 삭제 오류");
          return response.json();
        })
        .then(result => {
          if(alertFlag){
            if (result > 0) {
              alert("삭제가 완료되었습니다.");
              modalBackground.style.display = "none";
              selectBoardList(1);
            } else {
              alert("삭제 실패");
            }
            alertFlag = false;
          }
        })
        .catch(error => console.error("에러 발생:", error));
    };


    const ignoresHandler = () => {
      if ( !alertFlag && confirm("정말 삭제 하시겠습니까?") === false) {
        return;
      }
      alertFlag = true;
      
      fetch(`/manager/transaction/deleteReport?boardNo=${boardNo}`)
        .then(response => {
          if (!response.ok) throw new Error("게시글 삭제 오류");
          return response.json();
        })
        .then(result => {
          if(alertFlag){
            if (result > 0) {
              alert(":처리가 완료되었습니다.");
              modalBackground.style.display = "none";
              selectBoardList(1);
            } else {
              alert("처리 실패");
            }
            alertFlag = false;
          }
        })
        .catch(error => console.error("에러 발생:", error));
    };


    // 이전 이벤트 리스너 제거
    delBtn.removeEventListener("click", deleteHandler);
    // 새 이벤트 리스너 추가
    delBtn.addEventListener("click", deleteHandler);
    // 이전 이벤트 리스너 제거
    closeModal.removeEventListener("click", ignoresHandler);
    // 새 이벤트 리스너 추가
    closeModal.addEventListener("click", ignoresHandler);

    modalBackground.style.display = "flex"; // 모달 표시

  }else {
    console.error("해당 게시물의 정보를 찾을 수 없습니다.");
  }
  
  if(report){
    document.querySelector(".report-member").innerText = report.reportNickname;
    document.querySelector(".report-date").innerText = report.reportWriteDate;
    const div = document.createElement("div");
    div.innerHTML = report.reportCategoryContent; // HTML 삽입
    document.querySelector(".report-content").innerText = div.textContent;
    
    const ignoresHandler = () => {
      alertFlag = true;
      
      fetch(`/manager/transaction/deleteReport?boardNo=${boardNo}`)
      .then(response => {
        if (!response.ok) throw new Error("게시글 처리 오류");
        return response.json();
      })
      .then(result => {
        if(alertFlag){
          if (result > 0) {
            alert(":처리가 완료되었습니다.");
            modalBackground.style.display = "none";
            selectReportBoardList(1);
          } else {
            alert("처리 실패");
          }
          alertFlag = false;
        }
      })
      .catch(error => console.error("에러 발생:", error));
    };
    
    closeModal.addEventListener("click", ignoresHandler);
  } else {
    console.error("해당 게시물의 정보를 찾을 수 없습니다.");
  }
}




// 항목 렌더링 함수
const renderItems = (boardList, reportBoardList , currentPage, limit) => {
  boardListElement.innerHTML = ''; // 기존 내용 초기화
  const fragment = document.createDocumentFragment(); // Document Fragment 생성

  boardList.forEach((board, index) => {

    const tr = document.createElement("tr");
    
    const td1 = document.createElement("td");
    td1.innerText = ((currentPage - 1) * limit) + index + 1;
    
    const td2 = document.createElement("td");
    td2.innerText = board.boardTitle;
    
    const td3 = document.createElement("td");
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = reportBoardList[index].reportCategoryContent; // 수정된 부분
    td3.textContent = tempDiv.textContent;
    
    const td4 = document.createElement("td");
    td4.innerText = reportBoardList[index].reportWriteDate;
    
    const td5 = document.createElement("td");
    td5.innerText = board.memberNickname;
    
    const td6 = document.createElement("td");
    td6.innerText = board.reportCount;
    
    const td7 = document.createElement("td");
    const detailBtn = document.createElement("button");
    detailBtn.innerText = "상세정보";
    detailBtn.onclick = () => showBoardDetails(board.boardNo);
    td6.append(detailBtn);
    
    tr.append(td1, td2, td3, td4, td5, td6, td7);
    fragment.appendChild(tr); // Fragment에 추가
  });
  
  boardListElement.appendChild(fragment); // 한번에 DOM에 추가
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




// DOMContentLoaded 이벤트
document.addEventListener("DOMContentLoaded", () => {
  selectReportBoardList(1);
  modalBackground.style.display = "none";

});
