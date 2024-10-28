  /* 회원정보 수정 모달 창 */
  const modifyModal = document.querySelector("#modifyModal");

  const modifyMenu = document.querySelector("#modifyMenu");

  const modifyBtn = document.querySelector('#modifyBtn');
  const modifyCancelBtn = document.querySelector('#modifyCancelBtn');


  // 모달창 열기
  modifyMenu.addEventListener("click", () => {
    
    modifyModal.style.display = "flex";
  });


  modifyCancelBtn.addEventListener("click",() => {

    modifyModal.style.display = "none";
  });


/* 회원탈퇴 모달 창 */
const deleteModal = document.querySelector("#deleteModal");

const deleteMenu = document.querySelector("#deleteMenu");

const deleteBtn = document.querySelector('#deleteBtn');
const deleteCancelBtn = document.querySelector('#deleteCancelBtn');


deleteMenu.addEventListener("click", (e) => {
    
  deleteModal.style.display = "flex";
});


deleteCancelBtn.addEventListener("click",() => {

  deleteModal.style.display = "none";
  
});


/* 페이지 네이션 함수 */
const renderPagination = (pagination) => {
  
  const paginationBox = document.querySelector(".pagination-box");

  paginationBox.innerHTML = '';  // 기존 페이지 버튼 초기화

  // 페이지네이션 버튼을 생성하는 헬퍼 함수
  const createPageButton = (page, text, isActive = false) => {

    const button = document.createElement("a"); // a 요소 생성

    button.href = "#"; // 버튼 경로이동 막기

    button.classList.add("page-btn"); // 버튼 클래스 추가

    button.dataset.page = page; // 현재 페이지 

    button.textContent = text;  // 버튼에 들어갈 내용

    if (isActive) button.classList.add("active"); // 활성화된 페이지에 'active' 클래스 추가

    // 클릭 이벤트 추가
    button.addEventListener("click", (event) => {

      event.preventDefault();  // 링크 이동 막기 

      const cp = parseInt(event.target.dataset.page);

      selectLikeList(cp); // 해당 페이지로 이동
    });

    return button;
  };

  // <<, < 버튼 추가
  paginationBox.appendChild(createPageButton(1, "<<"));
  // 이전 페이지 그룹의 마지막 번호.
  paginationBox.appendChild(createPageButton(pagination.prevPage, "<"));

  // 동적 페이지 번호 버튼 생성
  for (let i = pagination.startPage; i <= pagination.endPage; i++) {
    // 현재 페이지와 반복문 i가 같으면 activce 클래스 추가
    const isActive = i === pagination.currentPage;
    paginationBox.appendChild(createPageButton(i, i, isActive));
  }

  // >, >> 버튼 추가
  // 다음 페이지 그룹의 첫번째 번호.
  paginationBox.appendChild(createPageButton(pagination.nextPage, ">"));
  paginationBox.appendChild(createPageButton(pagination.maxPage, ">>"));
};



/* 좋아요 게시물 비동기 */
const likeList = document.querySelector("#likeList");


const selectLikeList = (cp) => {
  const memberNo = document.querySelector("#memberNo").innerText; // memberNo를 가져옴

  let requestUrl = `/myPage/selectLikeList?memberNo=${memberNo}&cp=${cp}`; // cp 값 추가

  fetch(requestUrl)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .then(map => {
      const list = map.likeList;
      const pagination = map.pagination;

      console.log(list);
      console.log(pagination);

      renderPagination(pagination);  // 페이지네이션 렌더링 호출

      const paginationBox = document.querySelector(".pagination-box");

      document.querySelectorAll(".like-item").forEach(item => item.remove());

      list.forEach(board => {
        const likeItemDiv = document.createElement("div");
        likeItemDiv.className = "like-item";

        const titleDiv = document.createElement("div");
        titleDiv.className = "like-item-title";
        titleDiv.innerText = board.boardContent;

        const subDiv = document.createElement("div");
        subDiv.className = "like-item-sub";
        subDiv.innerText = `(${board.boardTitle})`;

        likeItemDiv.appendChild(titleDiv);
        likeItemDiv.appendChild(subDiv);

        likeList.insertBefore(likeItemDiv, paginationBox);
      });
    })
    .catch(err => console.error(err));
};


// 초기 데이터 로딩
document.addEventListener("DOMContentLoaded", () => {
  selectLikeList(1); // 기본 페이지 1로 데이터 로딩
});
