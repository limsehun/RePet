const memberList = document.querySelector("#memberList");

const selectMemberList = (cp = 1) => {
  fetch("/manager/selectMemberList?cp=" + cp)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("에러");
    })
    .then(map => {
      console.log(map);

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
        statusButton.id="status-btn";

        let currentIndex = 0; // Initialize currentIndex here
        const statuses = ['정상', '경고', '탈퇴'];
        statusButton.innerText = statuses[currentIndex];

        // Initial color setting
        updateButtonColor();

        td7.append(statusButton);
        tr.append(th1, td2, td3, td4, td5, td6, td7);
        memberList.append(tr);

        statusButton.addEventListener("click", () => {
          currentIndex = (currentIndex + 1) % statuses.length; // Cycle through statuses
          statusButton.textContent = statuses[currentIndex];

          // Update button color
          updateButtonColor();

          fetch("/changeStatus", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ memberNo: member.memberNo, status: statuses[currentIndex] }) // Adjust body as needed
          })
            .then(response => {
              if (response.ok) return response.text();
              throw new Error("경고 이력 변경 오류");
            })
            .then(result => {
              if (result > 0) {
                selectMemberList(); // Refresh member list
              } else {
                alert("변경 실패");
              }
            })
            .catch(err => console.error(err));
        });

        function updateButtonColor() {
          if (statuses[currentIndex] === '탈퇴') {
            statusButton.style.color = 'red'; // 글자색 빨간색
          } else if (statuses[currentIndex] === '정상'){
            statusButton.style.color = 'green';
          } else {
            statusButton.style.color = 'black'; // 글자색 검은색
          }
        }
      });
    })
    .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded", () => {
  selectMemberList();
});
