const closeBtn = document.querySelector(".close-button");
const manageBtn = document.querySelector(".manage-btn");
const modal = document.querySelector(".modal-overlay");




// 페이지 이동을 위한 버튼 모두 얻어오기
const pageNoList = document.querySelectorAll(".pagination a");


// 페이지 이동 버튼이 클릭 되었을 때
pageNoList?.forEach( (item, index) => {

  // 클릭 되었을 때
  item.addEventListener("click", e => {
    e.preventDefault();

    if(item.classList.contains("current")){
      return;
    } 

    // const -> let으로 변경
    let pathname = location.pathname; // 현재 게시판 조회 요청 주소


    switch(item.innerText){
      case '<<' :  
        pathname += "?cp=1";
        break;

      case '<'  :  
        pathname += "?cp=" + pagination.prevPage;
        break;

      case '>'  : 
        pathname += "?cp=" + pagination.nextPage;
        break;

      case '>>' : 
        pathname += "?cp=" + pagination.maxPage;
        break;

      default: 
        pathname += "?cp=" + item.innerText;
    }

    const params = new URLSearchParams(location.search);

    const key = params.get("key"); 
    const query = params.get("query");

    if(key !== null){ // 검색인 경우
      pathname += `&key=${key}&query=${query}`;
    }

    // 페이지 이동
    location.href = pathname;
  });
});

// ------------------------------------------------------------


(()=>{

  // 쿼리스트링 모두 얻어와 관리하는 객체
  const params = new URLSearchParams(location.search);

  const key = params.get("key");
  const query = params.get("query");

  if(key === null) return; // 검색이 아니면 함수 종료

  // 검색어 화면에 출력하기
  document.querySelector("#searchQuery").value = query;

  // 검색 조건 선택하기
  const options = document.querySelectorAll("#searchKey > option");

  options.forEach( op => {
    // op : <option> 태그
    if(op.value === key){ // option의 valeu와 key가 같다면
      op.selected = true; // selected 속성 추가
      return;
    }
  })

})();



// -------------- 모달창 -------------------



manageBtn.addEventListener("click", () => {
  modal.classList.remove("popup-hidden");
})

closeBtn.addEventListener("click", () => {
  modal.classList.add("popup-hidden");
})

