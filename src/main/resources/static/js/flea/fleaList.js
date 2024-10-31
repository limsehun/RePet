// 페이지 이동을 위한 버튼 모두 얻어오기
const pageNoList = document.querySelectorAll(".pagination a");

// 페이지 이동 버튼이 클릭 되었을 때
pageNoList?.forEach((item) => {
  // 클릭 되었을 때
  item.addEventListener("click", (e) => {
    e.preventDefault();

    // 만약 클릭된 a 태그에 "current" 클래스가 있을 경우
    if (item.classList.contains("current")) {
      return;
    }

    let pathname = location.pathname;

    // 클릭된 버튼이 <<, <, >, >> 인 경우
    switch (item.innerText) {
      case '<<':
        pathname += "?cp=1"; // 맨 앞 페이지 (1페이지)로 이동
        break;
      case '<':
        pathname += "?cp=" + pagination.prevPage; // 이전 페이지
        break;
      case '>':
        pathname += "?cp=" + pagination.nextPage; // 다음 페이지
        break;
      case '>>':
        pathname += "?cp=" + pagination.maxPage; // 마지막 페이지
        break;
      default:
        // 클릭한 숫자 페이지로 이동
        pathname += "?cp=" + item.innerText;
    }

    // URLSearchParams : 쿼리스트링을 관리하는 객체
    const params = new URLSearchParams(location.search);

    const key = params.get("key"); // K:V 중 K가 "key"인 요소의 값
    const query = params.get("query"); // K:V 중 K가 "query"인 요소의 값

    if (key !== null) { // 검색인 경우
      pathname += `&key=${key}&query=${query}`;
    }

    // 페이지 이동
    location.href = pathname;
  });
});

// ----------------------------------------------------------------------------------------------------

// 쿼리스트링에 검색 기록이 있을 경우 화면에 똑같이 선택/출력 하기
(() => {
  // 쿼리스트링 모두 얻어와 관리하는 객체
  const params = new URLSearchParams(location.search);

  const key = params.get("key");
  const query = params.get("query");

  if (key === null) return; // 검색이 아니면 함수 종료

  // 검색어 화면에 출력하기
  const searchQueryInput = document.querySelector("#searchQuery");
  if (searchQueryInput) {
    searchQueryInput.value = query;
  }

  // 검색 조건 선택하기 ==> 검색조건 4개를 일단 모두 선택
  const options = document.querySelectorAll("#searchKey > option");

  options.forEach(op => {
    // op : <option> 태그
    if (op.value === key) { // option 의 value 와 key 가 같다면
      op.selected = true; // selected 속성 추가
    }
  });
})();

// ----------------------------------------------------------------------------------------------------

// 글쓰기 버튼 클릭 시
const insertBtn = document.querySelector("#insertBtn");


  insertBtn?.addEventListener("click", () => {
    location.href = `/editFlea/insert`;
});
