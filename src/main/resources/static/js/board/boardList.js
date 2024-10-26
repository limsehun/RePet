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
        location.href = pathname + "?cp=1";
        break;

      case '<' :
        location.href = pathname + "?cp=" + pagination.prevPage;
        break;

      case '>' :
        location.href = pathname + "?cp=" + pagination.nextPage;
        break;

      case 'last' :
        location.href = pathname + "?cp=" + pagination.maxPage;
        break;

      default : location.href = location.pathname + "?cp=" + item.innerText;
      
    }

    


  })

})











/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");

insertBtn?.addEventListener("click", () => {

  const boardCode = location.pathname.split("/")[2];

  location.href = `/editBoard/${boardCode}/insert`;
})