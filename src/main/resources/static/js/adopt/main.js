
const plusBtn = document.querySelector("#plusBtn");
const adoptList = document.querySelector(".adoptList");

pageNoList?.forEach((item,index) => {
  
})


plusBtn.addEventListener("click",()=>{

  fetch("/adopt/main/page="+)
  .then(response =>{
    if(response.ok) return response.text();
    throw new Error("불러오기 실패")
  })
  .then(html => {
    console.log(html);
  })
  .catch(err => console.error(err));
})

