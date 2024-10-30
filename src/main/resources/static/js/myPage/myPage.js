

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
      // #memberNo 요소가 있는 경우에만 selectLikeList 호출
      if (document.querySelector("#memberNo")) {
        selectLikeList(cp); // 해당 페이지로 이동
      }

      // #boardList 요소가 있는 경우에만 selectBoardList 호출
      if (document.querySelector("#boardList")) {
        selectBoardList(cp); // 해당 페이지로 이동
      }   

      loadTabContent(currentTab, parseInt(event.target.dataset.page));
      
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

  if (!memberNo) {
    console.error("memberNo 요소가 존재하지 않습니다. 이 페이지에서는 selectLikeList를 실행하지 않습니다.");
    return; // 요소가 없으면 함수 종료
  }

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
      renderItems(list, '게시물');

      const paginationBox = document.querySelector(".pagination-box");

      document.querySelectorAll(".like-item").forEach(item => item.remove());

      list.forEach(board => {
        const likeItemDiv = document.createElement("div");
        likeItemDiv.className = "like-item";

        // 임시로 div를 만들어서 HTML을 넣은 다음 텍스트만 추출
        const tempDiv = document.createElement("div");
        tempDiv.innerHTML = board.boardContent; // HTML을 넣음
        const boardTextContent = tempDiv.textContent || tempDiv.innerText; // 텍스트만 추출

        const titleDiv = document.createElement("div");
        titleDiv.className = "like-item-title";
        titleDiv.innerText = boardTextContent.trim(); // 추출된 텍스트만 설정하고 앞뒤 공백 제거


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

  // 입력 필드 초기화
  document.querySelector("#memberPw").value = "";     // 기존 비밀번호 입력 초기화
  document.querySelector("#newPw").value = "";        // 새 비밀번호 입력 초기화
  document.querySelector("#confirmPw").value = "";    // 새 비밀번호 확인 초기화
  document.querySelector("#newNickname").value = "";  // 새 닉네임 입력 초기화

  // 에러 메시지 초기화
  document.querySelector("#memberPwMessage").innerText = "";
  document.querySelector("#confirmPwMessage").innerText = "";
  document.querySelector("#nickMessage").innerText = "";

  // 파일 선택 필드 초기화
  profileImageInput.value = "";
  lastValidFile = null;

  // 상태 초기화
  statusCheck = -1;
});



/* ----- 프로필 이미지 미리보기, 삭제하기 ----- */

// 프로필 이미지 업로드 상태에 따라서 어떤 상태인지 구분하는 값
// -1 : 프로필 이미지를 바꾼적이 없음(초기상태)
//  0 : 프로필 이미지 삭제(X버튼 클릭)
//  1 : 새 이미지 선택
let statusCheck = -1;

// 카메라 아이콘 클릭 시 파일 선택 창 열기
const cameraIcon = document.querySelector(".fa-camera");
cameraIcon.addEventListener("click", () => {
  profileImageInput.click();
});


// 이미지 들어갈 요소
const profileImg = document.querySelector("#profileImg");

// 이미지 첨부할 파일선택 창
const profileImageInput = document.querySelector("#profileImageInput");

// x 아이콘
const deleteImage = document.querySelector("#deleteImage");
const userDefaultImage = "/images/user.png"; // 기본 이미지 경로



if(profileImageInput != null) { // 프로필 변경 화면인 경우

  /** 미리 보기 함수 
   * @param file : input type="file"에서 선택된 파일
  */
  const updatePreview = (file) => {

    lastValidFile = file; // 선택된 파일을 lastValidFile에 대입(복사)

    // JS에서 제공하는 파읽을 읽어오는 객체
    const reader = new FileReader();

    // 파일을 읽어 오는데
    // DataURL 형식으로 읽어옴
    // DataURL: 파일 전체 데이터가 브라우저가 해석할 수 있는
    //          긴 주소형태 문자열로 변환
    reader.readAsDataURL(file);

    // 선택된 파일이 다 인식 되었을 때
    reader.addEventListener("load", e => {
      profileImg.src = e.target.result;
      // e.target.result == 파일이 변환된 주소 형태 문자열

      statusCheck = 1; // 새 파일이 선택된 상태 체크
    })
  }

profileImageInput.addEventListener("change", e => {

  // 선택된 파일 1개를 얻어온다.
  const file = e.target.files[0];

  // 선택된 파일이 없을 경우
  if(file === undefined) {
    /* 이전 선택한 파일 유지하는 코드 */
    // -> 이전 선택한 파일을 저장할 전역 변수(lastValidFile) 선언

    // 이전에 선택한 파일이 없는 경우
    // == 현재 페이지 들어와서 프로필 이미지 바꾼적이 없는 경우
    if(lastValidFile === null) return;


    // 이전에 선택한 파일이 "있을" 경우

    const dataTransfer = new DataTransfer();

    // DataTransfer가 가지고 있는 files 필드에
    // lastValidFile 추가
    dataTransfer.items.add(lastValidFile);

    // input의 files 변수에 lastValidFile이 추가된 files 대입
    profileImageInput.files = dataTransfer.files;

    // 이전 선택된 파일로 미리보기
    updatePreview(lastValidFile); 

    return;
  }

  // 선택된 파일이 있을 경우
  updatePreview(file); // 미리보기 함수 호출
})


deleteImage.addEventListener("click", () => {
  // 미리 보기를 기본 이미지로 변경
  profileImg.src = userDefaultImage;

  // input 태그와
  // 마지막 선택된 파일을 저장하는 lastValidFile에
  // 저장된 값을 모두 삭제

  profileImageInput.value = '';
  lastValidFile = null;

  statusCheck = 0; // 삭제 상태 체크
});


}



const modifyInfo = document.querySelector("#modifyForm");


// 
modifyInfo?.addEventListener("submit", e => {

  // 입력 요소 모두 얻어오기
  const memberPw = document.querySelector("#memberPw");
  const newPw = document.querySelector("#newPw");
  const confirmPw = document.querySelector("#confirmPw");
  const newNickname = document.querySelector("#newNickname");
  
  console.log(memberPw);
  console.log(newPw);
  console.log(confirmPw);
  console.log(newNickname);

  let str;
  
  // 빈 입력 필드를 순서대로 확인
  if (memberPw.value.trim().length == 0) {
    str = "기존 비밀번호를 입력해 주세요";

  } else if (newPw.value.trim().length == 0) {
      str = "새 비밀번호를 입력해 주세요";

  } else if (confirmPw.value.trim().length == 0) {
      str = "새 비밀번호 확인을 입력해 주세요";

  } else if (newNickname.value.trim().length == 0) {
      str = "새 닉네임을 입력해 주세요";
  }

  if (str !== undefined) { // 입력되지 않은 값이 존재
      alert(str);
      e.preventDefault(); // form 제출 막기
      return; // submit 이벤트 핸들러 종료
  }

  const lengthCheck = newPw.value.length >= 6 && newPw.value.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(newPw.value); // 영어 알파벳 포함
  const numberCheck = /\d/.test(newPw.value); // 숫자 포함
  const specialCharCheck = /[\!\@\#\_\-]/.test(newPw.value); // 특수문자 포함


  if ( !(lengthCheck && letterCheck && numberCheck && specialCharCheck) ) {
    alert("비밀번호를 영어,숫자,특수문자 1글자 이상, 6~20자 사이로 입력해주세요")
    e.preventDefault();
    return;
  }

  // 새 비밀번호와 확인 비밀번호가 같은지 체크
  if (newPw.value !== confirmPw.value) {
    alert("새 비밀번호가 일치하지 않습니다");
    e.preventDefault();
    return;
  }

  // 닉네임 유효성 검사 (최종 제출 시)
  const nicknameLengthCheck = newNickname.value.trim().length >= 3 && newNickname.value.trim().length <= 10;
  const nicknameValidCheck = /^[a-zA-Z0-9가-힣]{3,10}$/.test(newNickname.value.trim());

  if (!nicknameLengthCheck || !nicknameValidCheck) {
    alert("닉네임은 한글, 영어, 숫자로만 3~10 글자를 입력해주세요");
    e.preventDefault(); // form 제출 막기
    return;
  }
  


  let flag = true; // true인 경우 제출 불가능

  // 미변경 시 제출 불가
  if(statusCheck === -1) {
    flag = true;
  }

  // 기존 프로필 이미지 X -> 새 이미지 선택
  if(loginMemberProfileImg === null
    && statusCheck === 1) flag = false;
    
    
  // 기존 프로필 이미지 O -> x버튼을 눌러 삭제
  if(loginMemberProfileImg !== null
    && statusCheck === 0) flag = false;

  // 기존 프로필 이미지 O -> 새 이미지 선택
  if(loginMemberProfileImg !== null
    && statusCheck === 1) flag = false;

  if(flag === true) {
    e.preventDefault();
    alert("이미지 변경 후 제출해주세요.");
  }


});


/* ============= 유효성 검사(Validation) ============= */


// 입력 값이 유효한 형태인지 표시하는 객체(체크리스트)
const checkObj = {
  "memberPw" : true,
  "newPw" : true,
  "confirmPw" : true,
  "memberNickname" : true,
  "deletePw" : true
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

/* ------------------------------ myPage-modify JS  ------------------------------ */




/* ------------------------------ myPage-delete JS  ------------------------------ */


/* 회원탈퇴 모달 창 */
const deleteModal = document.querySelector("#deleteModal");

const deleteMenu = document.querySelector("#deleteMenu");

const deleteCancelBtn = document.querySelector('#deleteCancelBtn');


deleteMenu.addEventListener("click", () => {
    
  deleteModal.style.display = "flex";
});


deleteCancelBtn.addEventListener("click",(e) => {

  e.preventDefault(); // 기본 폼 제출 동작 막기

  deleteModal.style.display = "none";

  // 입력 필드 초기화
  document.querySelector("#deletePw").value = "";     // 기존 비밀번호 입력 초기화

  // 에러 메시지 초기화
  document.querySelector("#deletePwMessage").innerText = "";

});



const deletePw = document.querySelector("#deletePw");
const deletePwMessage = document.querySelector("#deletePwMessage");


deletePw.addEventListener("input", () => {
  
  const inputPw = deletePw.value.trim();

  if (inputPw.length === 0) { // 비밀번호 미입력
    deletePwMessage.innerText = "비밀번호를 입력해주세요."; // 기본 메시지 설정
    deletePwMessage.classList.remove("green", "red");
    checkObj.deletePw = false;
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
      deletePwMessage.innerText = "비밀번호가 일치합니다.";
      deletePwMessage.classList.remove("red");
      deletePwMessage.classList.add("green");
      checkObj.deletePw = true;

    } else { // 그 외의 경우 (0 또는 에러)
      deletePwMessage.innerText = "비밀번호가 일치하지 않습니다.";
      deletePwMessage.classList.remove("green");
      deletePwMessage.classList.add("red");
      checkObj.deletePw = false;
    }
  })
  
  .catch(err => console.error(err));
  
});


deleteForm?.addEventListener("submit", e => {
  e.preventDefault(); // 기본 폼 제출 방지

  // 비밀번호 유효성 검사 통과 여부 확인
  if (!checkObj.deletePw) {
    alert("비밀번호를 정확히 입력해주세요.");
    return;
  }

  // 서버로 탈퇴 요청 보내기
  fetch("/myPage/delete", {
    method: "PUT",
    headers: {"Content-Type": "application/json"},
  })
  .then(response => {
    console.log(response);
    if (response.ok) {
      return response.text();
    }
    throw new Error("회원 탈퇴 요청 실패");
  })
  .then(result => {
    
    if (result > 0) { // 탈퇴 성공 시
      alert("회원 탈퇴가 완료되었습니다.");
      // 메인 페이지나 로그인 페이지로 리다이렉트
      location.href = "/";
    } else {
      alert("회원 탈퇴에 실패했습니다. 다시 시도해주세요.");
    }
  })
  .catch(err => {
    console.error(err);
    alert("회원 탈퇴 처리 중 문제가 발생했습니다.");
  });
});

/* ------------------------------ myPage-delete JS  ------------------------------ */

const boardList = document.querySelector("#boardList");

const selectBoardList = (cp) => {
  let requestUrl = `/myPage/selectBoardList?cp=${cp}`; // cp 값 추가

  fetch(requestUrl)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .then(map => {
      const list = map.boardList;  // 'list' 이름 수정 -> 이 변수명은 서버에서 오는 데이터에 맞춰야 합니다.
      const pagination = map.pagination;

      console.log(list);
      console.log(pagination);

      renderPagination(pagination);  // 페이지네이션 렌더링 호출

      const paginationBox = document.querySelector(".pagination-box");

      // 기존 게시물 항목 삭제
      document.querySelectorAll(".like-item").forEach(item => item.remove());

      // 게시물 목록 렌더링
      list.forEach(board => {
        const likeItemDiv = document.createElement("div");
        likeItemDiv.className = "like-item";

        // 임시로 div를 만들어서 HTML을 넣은 다음 텍스트만 추출
        const tempDiv = document.createElement("div");
        tempDiv.innerHTML = board.boardContent; // HTML을 넣음
        const boardTextContent = tempDiv.textContent || tempDiv.innerText; // 텍스트만 추출

        const titleDiv = document.createElement("div");
        titleDiv.className = "like-item-title";
        titleDiv.innerText = boardTextContent.trim(); // 추출된 텍스트만 설정하고 앞뒤 공백 제거

        likeItemDiv.addEventListener("click", () => {
          // 게시물 상세 페이지로 이동
          location.href = `/board/2/${board.boardNo}`;
        });

        const subDiv = document.createElement("div");
        subDiv.className = "like-item-sub";
        subDiv.innerText = `(${board.boardTitle})`;

        likeItemDiv.appendChild(titleDiv);
        likeItemDiv.appendChild(subDiv);

        boardList.insertBefore(likeItemDiv, paginationBox);  // 'likeList' -> 'boardList'로 수정
      });

    })
    .catch(err => console.error(err));
};


/* 게시물, 댓글 리스트 페이지 변경 */

let currentTab = 'posts'; // 현재 탭 상태 ('posts' 또는 'comments')


const switchTab = (tab) => {
  currentTab = tab; // 현재 탭 변경
  document.querySelectorAll(".board-header div").forEach(div => div.classList.remove("active-tab"));
  document.querySelector(`#tab-${tab}`).classList.add("active-tab");

  loadTabContent(tab, 1); // 탭 전환 시 페이지를 1로 초기화하고 데이터 로딩
};

const loadTabContent = (tab, cp) => {
  if (tab === 'posts') {
    selectBoardList(cp);
  } else if (tab === 'comments') {
    selectCommentList(cp);
  }
};


/* 댓글 비동기 조회 */
const selectCommentList = (cp) => {
  let requestUrl = `/myPage/selectCommentList?cp=${cp}`; // 댓글 목록 가져오기 위한 URL

  fetch(requestUrl)
    .then(response => response.ok ? response.json() : Promise.reject("조회 오류"))
    
    .then(map => {

      const list = map.commentList;
      const pagination = map.pagination;

      renderPagination(pagination);  // 페이지네이션 렌더링 호출
      renderItems(list, '댓글');
    })
    .catch(err => console.error(err));
};


/* 항목 렌더링 함수 (게시물/댓글) */
const renderItems = (list, type) => {
  const likeItemsContainer = document.querySelector("#like-items");
  likeItemsContainer.innerHTML = ''; // 기존 항목 초기화

  list.forEach(item => {
    const likeItemDiv = document.createElement("div");
    likeItemDiv.className = "like-item";

    const titleDiv = document.createElement("div");
    titleDiv.className = "like-item-title";
    titleDiv.innerText = item.title || item.content;

    likeItemDiv.addEventListener("click", () => {
      // 게시물이나 댓글 상세 페이지로 이동
      if (type === '게시물') {
        location.href = `/board/2/${item.boardNo}`;
      } else if (type === '댓글') {
        location.href = `/comment/${item.commentNo}`;
      }
    });

    const subDiv = document.createElement("div");
    subDiv.className = "like-item-sub";
    subDiv.innerText = `(${type})`;

    likeItemDiv.appendChild(titleDiv);
    likeItemDiv.appendChild(subDiv);
    likeItemsContainer.appendChild(likeItemDiv);
  });
};



/* ------------------------------ myPage-board JS  ------------------------------ */




document.addEventListener("DOMContentLoaded", () => {
  // 페이지에 #memberNo 요소가 존재할 때만 selectLikeList 호출
  if (document.querySelector("#memberNo")) {
    selectLikeList(1); // 기본 페이지 1로 데이터 로딩
  }

  // 페이지에 #boardList 요소가 존재할 때만 selectBoardList 호출
  if (document.querySelector("#boardList")) {
    selectBoardList(1); // 기본 페이지 1로 데이터 로딩
  }
});