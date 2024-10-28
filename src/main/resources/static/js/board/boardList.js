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

    location.href = pathname;

   



    


  })

})















/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");

insertBtn?.addEventListener("click", () => {

  // const boardCode = location.pathname.split("/")[2];

  location.href = `/editBoard/2/insert`;
})