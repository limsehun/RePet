

/* ------------------------------ myPage-info JS  ------------------------------ */
/* 페이지 네이션 함수 */
const renderPagination = (pagination) => {
  
  const paginationBox = document.querySelector(".pagination-box");

  paginationBox.innerHTML = '';  // 기존 페이지 버튼 초기화

  // 페이지네이션 버튼을 생성하는 헬퍼 함수
  const createPageButton = (page, text, isActive = false) => {

    const button = document.createElement("a"); // a 요소 생성

    button.href = "#"; // 버튼 경로이동 막기

    button.classList.add("page-btn"); // 버튼 클래스 추가

    button.dataset.page = page; // 현재 페이지 

    button.textContent = text;  // 버튼에 들어갈 내용

    if (isActive) button.classList.add("active"); // 활성화된 페이지에 'active' 클래스 추가

    // 클릭 이벤트 추가
    button.addEventListener("click", (event) => {

      event.preventDefault();  // 링크 이동 막기 

      const cp = parseInt(event.target.dataset.page);

      selectLikeList(cp); // 해당 페이지로 이동
    });

    return button;
  };

  // <<, < 버튼 추가
  paginationBox.appendChild(createPageButton(1, "<<"));
  // 이전 페이지 그룹의 마지막 번호.
  paginationBox.appendChild(createPageButton(pagination.prevPage, "<"));

  // 동적 페이지 번호 버튼 생성
  for (let i = pagination.startPage; i <= pagination.endPage; i++) {
    // 현재 페이지와 반복문 i가 같으면 activce 클래스 추가
    const isActive = i === pagination.currentPage;
    paginationBox.appendChild(createPageButton(i, i, isActive));
  }

  // >, >> 버튼 추가
  // 다음 페이지 그룹의 첫번째 번호.
  paginationBox.appendChild(createPageButton(pagination.nextPage, ">"));
  paginationBox.appendChild(createPageButton(pagination.maxPage, ">>"));
};



/* 좋아요 게시물 비동기 */
const likeList = document.querySelector("#likeList");


const selectLikeList = (cp) => {
  const memberNo = document.querySelector("#memberNo").innerText; // memberNo를 가져옴

  let requestUrl = `/myPage/selectLikeList?memberNo=${memberNo}&cp=${cp}`; // cp 값 추가

  fetch(requestUrl)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .then(map => {

      const list = map.likeList;
      const pagination = map.pagination;

      console.log(list);
      console.log(pagination);

      renderPagination(pagination);  // 페이지네이션 렌더링 호출

      const paginationBox = document.querySelector(".pagination-box");

      document.querySelectorAll(".like-item").forEach(item => item.remove());

      list.forEach(board => {
        const likeItemDiv = document.createElement("div");
        likeItemDiv.className = "like-item";

        const titleDiv = document.createElement("div");
        titleDiv.className = "like-item-title";
        titleDiv.innerText = board.boardContent;


        console.log(board.boardNo);


      likeItemDiv.addEventListener("click", () => {
        // 게시물 상세 페이지로 이동
        location.href = `/board/2/${board.boardNo}`;
      });

        const subDiv = document.createElement("div");
        subDiv.className = "like-item-sub";
        subDiv.innerText = `(${board.boardTitle})`;

        likeItemDiv.appendChild(titleDiv);
        likeItemDiv.appendChild(subDiv);

        likeList.insertBefore(likeItemDiv, paginationBox);
      });
    })
    .catch(err => console.error(err));
};

/* ------------------------------ myPage-info JS  ------------------------------ */


/* ------------------------------ myPage-modify JS  ------------------------------ */

/* 회원정보 수정 모달 창 */
const modifyModal = document.querySelector("#modifyModal");

const modifyMenu = document.querySelector("#modifyMenu");

const modifyBtn = document.querySelector('#modifyBtn');
const modifyCancelBtn = document.querySelector('#modifyCancelBtn');


// 모달창 열기
modifyMenu.addEventListener("click", () => {
  
  modifyModal.style.display = "flex";
});

// 모달창 닫기
modifyCancelBtn.addEventListener("click",() => {

  modifyModal.style.display = "none";
});



modifyBtn.addEventListener('click', () => {
  // 비밀번호 일치 여부 확인
  if (newPw.value !== confirmPw.value) {
    alert("비밀번호가 일치하지 않습니다."); // 사용자에게 알림
    return; // 함수 종료
  }

  // 유효성 검사 통과 시 수정 작업 진행
  // 여기에서 서버에 수정 요청을 보내는 코드 추가
});


/* ============= 유효성 검사(Validation) ============= */


// 입력 값이 유효한 형태인지 표시하는 객체(체크리스트)
const checkObj = {
  "memberPw" : true,
  "newPw" : true,
  "confirmPw" : true,
  "memberNickname" : true
}

// 비밀번호 유효성 검사
const memberPw = document.querySelector("#memberPw");
const memberPwMessage = document.querySelector("#memberPwMessage");

// 새 비밀번호 유효성 검사
const newPw = document.querySelector("#newPw");
const confirmPw = document.querySelector("#confirmPw");
const confirmPwMessage = document.querySelector("#confirmPwMessage");

// 회원 비밀번호 검사
const pwMessageObj = {};
pwMessageObj.normal = "비밀번호가 일치하지 않습니다.";

// 새로운 비밀번호 검사
const confirmPwMessageObj = {};
confirmPwMessageObj.normal = "영어,숫자,특수문자 1글자 이상, 6~20자 사이.";
confirmPwMessageObj.invaild = "유효하지 않은 비밀번호 형식입니다.";
confirmPwMessageObj.vaild = "유효한 비밀번호 형식입니다.";
confirmPwMessageObj.error = "비밀번호가 일치하지 않습니다.";
confirmPwMessageObj.check = "비밀번호가 일치 합니다.";


memberPw.addEventListener("input", () => {
  
  const inputPw = memberPw.value.trim();

  if (inputPw.length === 0) { // 비밀번호 미입력
    memberPwMessage.innerText = "비밀번호를 입력해주세요."; // 기본 메시지 설정
    memberPwMessage.classList.remove("green", "red");
    checkObj.memberPw = false;
    return; 
  }

  // 서버 응답에 따라 메시지 변경
  fetch("/myPage/checkPw?inputPw=" + inputPw)
  .then(response => {
    if(response.ok) { // HTTP 응답 상태 코드 200번(응답 성공)
      return response.text(); // 응답 결과를 text로 파싱
    }

    throw new Error("비밀번호 검사 에러");
  })
  .then(result => {

    console.log(result);

    if(result === "1") { // 서버에서 1을 반환하면
      memberPwMessage.innerText = "비밀번호가 일치합니다.";
      memberPwMessage.classList.remove("red");
      memberPwMessage.classList.add("green");
      checkObj.memberPw = true;

    } else { // 그 외의 경우 (0 또는 에러)
      memberPwMessage.innerText = "비밀번호가 일치하지 않습니다.";
      memberPwMessage.classList.remove("green");
      memberPwMessage.classList.add("red");
      checkObj.memberPw = false;
    }
  })
  
  .catch(err => console.error(err));
  
});


// 비밀번호 확인
newPw.addEventListener("input", () => {
  
  const inputNewPw = newPw.value.trim();

  if(inputNewPw.length === 0){ // 비밀번호 미입력
    confirmPwMessage.innerText = confirmPwMessageObj.normal;
    confirmPwMessage.classList.remove("red", "green");
    checkObj.newPw = false;
    newPw.value = "";
    return;
  }

  // 비밀번호 정규표현식 검사
  const lengthCheck = inputNewPw.length >= 6 && inputNewPw.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(inputNewPw); // 영어 알파벳 포함
  const numberCheck = /\d/.test(inputNewPw); // 숫자 포함
  const specialCharCheck = /[\!\@\#\_\-]/.test(inputNewPw); // 특수문자 포함

  // 조건이 하나라도 만족하지 못하면
  if ( !(lengthCheck && letterCheck && numberCheck && specialCharCheck) ) {
    confirmPwMessage.innerText = confirmPwMessageObj.invaild;
    confirmPwMessage.classList.remove("green");
    confirmPwMessage.classList.add("red");
    checkObj.newPw = false;
    return;
  }


  confirmPwMessage.innerText = confirmPwMessageObj.vaild;
  confirmPwMessage.classList.remove("red");
  confirmPwMessage.classList.add("green");
  checkObj.newPw = true;


  // 비밀번호 확인이 작성된 상태에서
  // 비밀번호가 입력된 경우
  if(confirmPw.value.trim().length > 0){
    checkPw(); // 같은지 비교하는 함수 호출
  }

});


function checkPw(){

  // 같은 경우
  if(newPw.value === confirmPw.value){
    confirmPwMessage.innerText = confirmPwMessageObj.check;
    confirmPwMessage.classList.add("green");
    confirmPwMessage.classList.remove("red");
    checkObj.confirmPw = true;
    return;
  }

  // 다른 경우
  confirmPwMessage.innerText = confirmPwMessageObj.error;
  confirmPwMessage.classList.add("red");
  confirmPwMessage.classList.remove("green");
  checkObj.confirmPw = false;
}



/* ----- 비밀번호 확인이 입력 되었을 때  ----- */
confirmPw.addEventListener("input", () => {

  // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
  if(checkObj.newPw === true){
    checkPw();
    return;
  }

  // 비밀번호 input에 작성된 값이 유효하지 않은 경우
  checkObj.confirmPw = false;
});




/* 닉네임 검사 */
// - 3글자 이상
// - 중복 X
const newNickname = document.querySelector("#newNickname");
const nickMessage = document.querySelector("#nickMessage");

const nickMessageObj = {};
nickMessageObj.normal = "한글,영어,숫자로만 3~10 글자를 입력하세요.";
nickMessageObj.invalid = "유효하지 않은 닉네임 형식 입니다";
nickMessageObj.duplication = "이미 사용중인 닉네임 입니다.";
nickMessageObj.check = "사용 가능한 닉네임 입니다.";

newNickname.addEventListener("input", () => {

  // 입력 받은 닉네임
  const inputNickname = newNickname.value.trim();

  // 4) 입력된 닉네임이 없을 경우
  if(inputNickname.length === 0) {

    nickMessage.innerText = nickMessageObj.normal;

    nickMessage.classList.remove("green", "red");

    checkObj.memberNickname = false;

    memberNickname.value = ""; 

    return;
  }

  // 5) 닉네임 유효성 검사(정규 표현식)
  const regEx = /^[a-zA-Z0-9가-힣]{3,10}$/; // 한글,영어,숫자로만 3~10글자

  if( regEx.test(inputNickname) === false ) {
    nickMessage.innerText = nickMessageObj.invalid; // 유효 x 메시지
    nickMessage.classList.add("red"); // 빨간 글씨 추가
    nickMessage.classList.remove("green"); // 빨간 글씨 추가
    checkObj.memberNickname = false;
    return;
  }

  // 6) 닉네임 중복 검사
  fetch("/myPage/nicknameCheck?nickname=" + inputNickname)
  .then(response => {
    if(response.ok) { // HTTP 응답 상태 코드 200번(응답 성공)
      return response.text(); // 응답 결과를 text로 파싱
    }

    throw new Error("닉네임 중복 검사 에러");
  })
  .then(count => {

    // 매개 변수 count : 첫 번째 then에서 return된 값이 저장된 변수

    if(count == 1){ // 중복인 경우
      nickMessage.innerText = nickMessageObj.duplication; // 중복 메시지
      nickMessage.classList.add("red");
      nickMessage.classList.remmove("green");
      checkObj.memberNickname = false;
      return;
    }

    // 중복이 아닌 경우
    nickMessage.innerText = nickMessageObj.check; // 중복X 메시지
    nickMessage.classList.add("green");
    nickMessage.classList.remove("red");
    checkObj.memberNickname = true; 
  })

  .catch( err => console.error(err) );

});






/* ------------------------------ myPage-delete JS  ------------------------------ */
/* 회원탈퇴 모달 창 */
const deleteModal = document.querySelector("#deleteModal");

const deleteMenu = document.querySelector("#deleteMenu");

const deleteBtn = document.querySelector('#deleteBtn');
const deleteCancelBtn = document.querySelector('#deleteCancelBtn');


deleteMenu.addEventListener("click", (e) => {
    
  deleteModal.style.display = "flex";
});


deleteCancelBtn.addEventListener("click",() => {

  deleteModal.style.display = "none";
});





// 초기 데이터 로딩
document.addEventListener("DOMContentLoaded", () => {
  selectLikeList(1); // 기본 페이지 1로 데이터 로딩
});