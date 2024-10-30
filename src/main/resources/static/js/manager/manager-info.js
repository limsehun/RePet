
const memberList = document.querySelector("#memberList");

const selectMemberList = (cp = 1) => {
  fetch("/manager/selectMemberList?cp=" + cp)
  .then(response => { 
    if(response.ok) return response.json();
    throw new Error("에러")
  })
  .then(map=>{
    console.log(map);

    const list = map.memberList;
    const pagination = map.pagination;

    // memberList.innerHTML = "";

    list.forEach(member => {

      const tr = document.createElement("tr");

      // 회원 번호
      const th1 = document.createElement("td");
      th1.innerText = member.memberNo;

      // 회원 이메일
      const td2 = document.createElement("td");
      td2.innerText = member.memberEmail;

      // 회원 닉네임
      const td3 = document.createElement("td");
      td3.innerText = member.memberNickname;

      // 회원 가입일
      const td4 = document.createElement("td");
      td4.innerText = member.enrollDate;

      // 회원이 쓴 게시글 수
      const td5 = document.createElement("td");
      td5.innerText = member.boardCount;

      // 회원이 쓴 댓글 수
      const td6 = document.createElement("td");
      td6.innerText = member.commentCount;

      const td7 = document.createElement("td");
      const status = document.createElement("span");
      status.innerText = "정상";
      td7.append(status);

      tr.append(th1, td2, td3, td4, td5, td6, td7);

      status.addEventListener("click", () => {
        fetch("/changeStatus", {
          method  : "PUT",
          headers : {"Content-Type" : "application/json"},
          body    :  member.memberNo 
        })
        .then(response =>{
          if(response.ok) return response.text();
          throw new Error("경고 이력 변경 오류");
        })
        .then(result => {
          if(result > 0){
            selectMemberList();

          } else {
            alert("변경 실패");
          }

        })
        .catch(err => console.error(err));
      })

      // #memberList에 tr 추가
      memberList.append(tr);

    })
  })
  .catch(err => console.error(err));

}

document.addEventListener("DOMContentLoaded", () => {
  selectMemberList();
})