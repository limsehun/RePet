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



/* 좋아요 게시물 비동기 */
const likeList = document.querySelector("#likeList");


const selectLikeList = () => {
  const memberNo = document.querySelector("#memberNo").innerText; // memberNo를 가져옴
  
  fetch(`/myPage/selectLikeList?memberNo=${memberNo}`)
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {

    console.log(list);

    const paginationBox = document.querySelector(".pagination-box");

    
    list.forEach(board => {
      // like-item div 생성
      const likeItemDiv = document.createElement("div");
      likeItemDiv.className = "like-item"; // 클래스명 추가

      // like-item-title div 생성
      const titleDiv = document.createElement("div");
      titleDiv.className = "like-item-title"; // 클래스명 추가
      titleDiv.innerText = board.boardContent; // 텍스트 추가

      // like-item-sub div 생성
      const subDiv = document.createElement("div");
      subDiv.className = "like-item-sub"; // 클래스명 추가
      subDiv.innerText = `(${board.boardTitle})`; // 텍스트 추가 (괄호 추가)

      // like-item div에 title과 sub 추가
      likeItemDiv.appendChild(titleDiv);
      likeItemDiv.appendChild(subDiv);

      // 생성한 like-item div를 likeList에 추가
      likeList.insertBefore(likeItemDiv, paginationBox);
    });
  })
  .catch(err => console.error(err));
};


document.addEventListener("DOMContentLoaded", () => {
  selectLikeList();
})








