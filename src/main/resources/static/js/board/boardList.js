/* 페이지네이션 a태그 요소들 모두 얻어오기 */
const paginationList = document.querySelectorAll(".pagination a");

paginationList?.forEach((item, idx) => {

  item.addEventListener("click", e => {
    e.preventDefault();

    if(item.classList.contains("current")){
      return;
    }


    let pathname = location.pathname;

    switch(item.innerText){
      case 'first' : 
        location.href = pathname + "?cpage=1";
        break;

        case '<'  : // 이전 페이지
        pathname += "?cpage=" + pagination.prevPage;
        break;
        case '>'  : // 다음 페이지
         pathname += "?cpage=" + pagination.nextPage;
        break;
       case 'last' : // 마지막 페이지
        pathname += "?cpage=" + pagination.maxPage;
        break;

        default: 
         pathname += "?cpage=" + item.innerText;
  
      }

       /* 검색인 경우 pathname 변수에 뒤에 쿼리스트링 추가 */

    // URLSearchParams : 쿼리스트링을 관리하는 객체
    // - 쿼리스트링 생성, 기존 쿼리 스트링을 k:v 형태로 분할 관리
    const params = new URLSearchParams(location.search);

    const key = params.get("key"); // K가 "key"인 요소의 값
    const query = params.get("query"); // k가 "query"인 요소의 값

    if(key !== null){ // 검색인 경우
      pathname += `&key=${key}&query=${query}`;
    }
      
      location.href = pathname;
  });
});

(()=>{
  // 쿼리 스트링 모두 얻어와 관리하는 객체
  const params = new URLSearchParams(location.search);

  const key = params.get("key")
  const query = params.get("query")

  if(key === null) return; // 검색이 아니면 함수 종료

  // 검색어 화면에 출력하기
  document.querySelector("#searchQuery").value = query;

  // 검색 조건 선택하기
  const options = document.querySelectorAll("#searchKey > option");

  options.forEach( op => {
    // op : <option> 태그
    if(op.value === key){ // option의 value와 key가 같다면
      op.selected = true; // selected 속성 추가
      return;
    }
  });

})();

     



   



    


















/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");

insertBtn?.addEventListener("click", () => {

  // const boardCode = location.pathname.split("/")[2];

  location.href = `/editBoard/2/insert`;
})