const memberList = document.querySelector("#memberList");

const selectMemberList = (cp = 1) => {
  fetch("/manager/selectMemberList?cp=" + cp)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("에러");
    })
    .then(map => {


      const list = map.memberList;
      const pagination = map.pagination;

      memberList.innerHTML = ""; // Clear previous list

      list.forEach(member => {
        const tr = document.createElement("tr");

        // Create member data cells
        const th1 = document.createElement("td");
        th1.innerText = member.memberNo;

        const td2 = document.createElement("td");
        td2.innerText = member.memberEmail;

        const td3 = document.createElement("td");
        td3.innerText = member.memberNickname;

        const td4 = document.createElement("td");
        td4.innerText = member.enrollDate;

        const td5 = document.createElement("td");
        td5.innerText = member.boardCount;

        const td6 = document.createElement("td");
        td6.innerText = member.commentCount;

        const td7 = document.createElement("td");
        const statusButton = document.createElement("button");
        statusButton.id = "status-btn";

        const td8 = document.createElement("td");
        const warningBtn = document.createElement("button");
        warningBtn.id = "warning-btn";

        renderPagination(pagination);

        warningBtn.innerText = '경고';

        // let currentIndex = 0; // Initialize currentIndex here
        // const statuses = ['정상', '경고', '탈퇴'];

        statusButton.innerText = member.memberDelFl === 'N' ? '정상' : '탈퇴';
        statusButton.style.color = member.memberDelFl === 'N' ? 'green' : 'red';

        td7.append(statusButton);
        td7.append(warningBtn);

        tr.append(th1, td2, td3, td4, td5, td6, td7, td8);
        memberList.append(tr);

        statusButton.addEventListener("click", () => {
          const btnText = statusButton.innerText;

          if (btnText === '정상' && !confirm("탈퇴 하시겠습니까?")) {
            return;
          }



          /* 탈퇴 여부 변경 */
          fetch("/manager/changeStatus", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: member.memberNo
          })
            .then(response => {
              if (response.ok) return response.text();
              throw new Error("탈퇴 변경 오류");
            })
            .then(result => {
              if (result > 0) {
                if (btnText === '정상') alert("탈퇴 메시지가 발송 되었습니다");

                statusButton.innerText = btnText === '정상' ? '탈퇴' : '정상';
                statusButton.style.color = btnText === '정상' ? 'red' : 'green';

              } else {
                alert("변경 실패");
              }

            })
            .catch(err => console.error(err));


          /* 메일 발송 */
          fetch("/email/secessionMessage", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: member.memberEmail
          })
            .then(response => {
              if (response.ok) return response.text();
              throw new Error("이메일 발송 실패");
            })
            .then(result => {
              // 백엔드 작성 후 나머지 코드 작성 예정
              console.log(result);

            })
            .catch(err => console.error(err));

        }); // statusButton end


        warningBtn.addEventListener("click", () => {
          confirm("경고 메시지를 보내시겠습니까?");

          // 게시글 작성자에게 알림 보내기
          const content2
            = `잘못된 게시글 작성으로 인해 경고 메시지를 보냅니다`;

          sendNotification(
            "warningAlarm",
            location.pathname, // 게시글 상세 조회 페이지 주소
            member.memberNo,
            content2
          );

        })


      });
    })
    .catch(err => console.error(err));
}





const paginationBox = document.querySelector(".pagination");

const renderPagination = (pagination) => {

  // let paginationBox;

  paginationBox.innerHTML = '';  // 기존 페이지 버튼 초기화

  const createPageButton = (page, text, isActive = false) => {
    const button = document.createElement("a");
    button.href = "#";
    button.classList.add("page-btn");
    button.dataset.page = page;
    button.textContent = text;

    if (isActive) button.classList.add("active");

    button.addEventListener("click", (event) => {
      event.preventDefault();
      const cp = parseInt(event.target.dataset.page);

      // 모든 페이지 버튼에서 active 클래스를 제거
      document.querySelectorAll(".page-btn").forEach(btn => btn.classList.remove("active"));

      // 현재 클릭된 버튼에 active 클래스 추가
      button.classList.add("active");

      selectMemberList(cp);
    });

    return button;
  };

  // <<, < 버튼 추가
  paginationBox.appendChild(createPageButton(1, "<<"));
  paginationBox.appendChild(createPageButton(pagination.prevPage, "<"));

  // 동적 페이지 번호 버튼 생성
  for (let i = pagination.startPage; i <= pagination.endPage; i++) {
    const isActive = i === pagination.currentPage;
    paginationBox.appendChild(createPageButton(i, i, isActive));
  }

  // >, >> 버튼 추가
  paginationBox.appendChild(createPageButton(pagination.nextPage, ">"));
  paginationBox.appendChild(createPageButton(pagination.maxPage, ">>"));
};


// DOMContentLoaded 이벤트
document.addEventListener("DOMContentLoaded", () => {
  selectMemberList();
});




// 검색 
(()=>{

  // 쿼리스트링 모두 얻어와 관리하는 객체
  const params = new URLSearchParams(location.search);

  const key = params.get("key");
  const query = params.get("query");

  if(key === null) return; //검색이 아니면 함수 종료

  document.querySelector("#searchQuery").value = query;

  const options = document.querySelectorAll("#searchKey > option");
  options.forEach( op => {
    // op : <option> 태그
    if(op.value === key){ // option의 valeu와 key가 같다면
      op.selected = true; // selected 속성 추가
      return;
    }
  })

})();
