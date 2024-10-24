/* 페이지네이션 a태그 요소들 모두 얻어오기 */
const paginationList = document.querySelectorAll(".pagination a");

















/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");

insertBtn?.addEventListener("click", () => {

  const boardCode = location.pathname.split("/")[2];

  location.href = `/editBoard/${boardCode}/insert`;
})