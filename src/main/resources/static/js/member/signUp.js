
/* 다음 주소 API로 주소 검색하기 */
function findAddress() {
  new daum.Postcode({
    oncomplete: function (data) {
      var addr = ''; // 주소 변수


      if (data.userSelectedType === 'R') {
        addr = data.roadAddress;
      } else {
        addr = data.jibunAddress;
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('postcode').value = data.zonecode;
      document.getElementById("address").value = addr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById("detailAddress").focus();
    }
  }).open();
}

// 주소 검색 버튼 클릭 시
const searchAddress = document.querySelector("#searchAddress");
searchAddress.addEventListener("click", findAddress);


const checkObj = {
  "memberEmail": false,
  "memberPw": false,
  "memberNickname": false,
  "check-btn": false,
  "submit-btn": false
};


/* ----- 이메일 유효성 검사 ----- */

//  이메일 유효성 검사에 필요한 요소
const checkBtn = document.querySelector("#check-btn");
const memberEmail = document.querySelector("#memberEmail");
const memberEmailMessage = document.querySelector("#emailMessage");


const memberEmailCM = {}; // 빈 객체
memberEmailCM.normal = "중복 되지 않는 이메일을 입력해주세요.";
memberEmailCM.invaild = "알맞은 이메일 형식으로 작성해 주세요.";
memberEmailCM.duplication = "이미 사용중인 이메일 입니다.";
memberEmailCM.check = "사용 가능한 이메일 입니다.";

// 이메일이 입력될 때 마다 유효성 검사를 수행
memberEmail.addEventListener("input", e => {
  const inputmemberEmail = memberEmail.value.trim();
  if (inputmemberEmail.length === 0) {
    memberEmailMessage.innerText = memberEmailCM.normal;
    memberEmailMessage.classList.remove("confirm", "error");
    checkObj.memberEmail = false;
    memberEmail.value = "";
    return;
  }

  const regEx = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


  if (regEx.test(inputmemberEmail) === false) {
    memberEmailMessage.innerText = memberEmailCM.invaild; // 유효 X 메시지
    memberEmailMessage.classList.add("error");
    memberEmailMessage.classList.remove("confirm");
    checkObj.memberEmail = false;
    return;
  }


  fetch("/member/memberEmailCheck?memberEmail=" + inputmemberEmail)
    .then(response => {
      if (response.ok) {
        return response.text()
      }

      throw new Error("이메일 중복 검사 에러");
    })
    .then(count => {

      // checkBtn.addEventListener("click", () => {

      if (count == 1) { // 중복인 경우
        memberEmailMessage.innerText = memberEmailCM.duplication; // 중복 메시지
        memberEmailMessage.classList.add("error");
        memberEmailMessage.classList.remove("confirm");
        checkObj.memberEmail = false;
        return;
      }

      // 중복이 아닌 경우
      memberEmailMessage.innerText = memberEmailCM.check; // 중복X 메시지
      memberEmailMessage.classList.add("confirm");
      memberEmailMessage.classList.remove("error");
      checkObj.memberEmail = true; // 유효한 이메일임을 기록

      // })

    })
    .catch(err => console.error(err));

});



const memberPw = document.querySelector("#memberPw");
// const memberPwCheck = document.querySelector("#memberPwCheck");
const pwMessage = document.querySelector("#pwMessage");

const pwCheckMessage = {};
pwCheckMessage.normal = "영어,숫자,특수문자 1글자 이상, 6~20자 사이.";
pwCheckMessage.invaild = "유효하지 않은 비밀번호 형식입니다.";
pwCheckMessage.vaild = "유효한 비밀번호 형식입니다.";
// pwCheckMessage.error = "비밀번호가 일치하지 않습니다.";
// pwCheckMessage.check = "비밀번호가 일치 합니다.";


memberPw.addEventListener("input", () => {

  const inputPw = memberPw.value.trim();

  if (inputPw.length === 0) { // 비밀번호 미입력
    pwMessage.innerText = pwCheckMessage.normal;
    pwMessage.classList.remove("confirm", "error");
    checkObj.memberPw = false;
    memberPw.value = "";
    return;
  }


  const lengthCheck = inputPw.length >= 6 && inputPw.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(inputPw);
  const numberCheck = /\d/.test(inputPw);
  const specialCharCheck = /[\!\@\#\_\-]/.test(inputPw);

  // 조건이 하나라도 만족하지 못하면
  if (!(lengthCheck && letterCheck && numberCheck && specialCharCheck)) {
    pwMessage.innerText = pwCheckMessage.invaild;
    pwMessage.classList.remove("confirm");
    pwMessage.classList.add("error");
    checkObj.memberPw = false;
    return;
  }

  pwMessage.innerText = pwCheckMessage.vaild;
  pwMessage.classList.remove("error");
  pwMessage.classList.add("confirm");
  checkObj.memberPw = true;


  // 비밀번호가 입력된 경우
  // if (memberPwCheck.value.trim().length > 0) {
  //   checkPw(); 
  // }
});

/* ----- 비밀번호, 비밀번호 확인 같은지 검사하는 함수 ----- */
// function checkPw() {

//   // 같은 경우
//   if (memberPw.value === memberPwCheck.value) {
//     pwMessage.innerText = pwCheckMessage.check;
//     pwMessage.classList.add("confirm");
//     pwMessage.classList.remove("error");
//     checkObj.memberPwCheck = true;
//     return;
//   }
//   pwMessage.innerText = pwCheckMessage.error;
//   pwMessage.classList.add("error");
//   pwMessage.classList.remove("confirm");
//   checkObj.memberPwCheck = false;
// }

// /* ----- 비밀번호 확인이 입력 되었을 때  ----- */
// memberPwCheck.addEventListener("input", () => {

//   // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
//   if (checkObj.memberPw === true) {
//     checkPw();
//     return;
//   }
//   // 비밀번호 input에 작성된 값이 유효하지 않은 경우
//   checkObj.memberPwCheck = false;
// });



/* ----- 닉네임 유효성 검사 ----- */
// 1) 닉네임 유효성 검사에 사용되는 요소 얻어오기
const signUpMemberNickname = document.querySelector("#memberNickname");
const checkBtn2 = document.querySelector("#check-btn2");
const nickMessage = document.querySelector("#nickMessage");

// 2) 닉네임 관련 메시지 작성
const nickCheck = {};
nickCheck.normal = "한글,영어,숫자로만 3~10글자";
nickCheck.invaild = "유효하지 않은 닉네임 형식 입니다";
nickCheck.duplication = "이미 사용중인 닉네임 입니다.";
nickCheck.check = "사용 가능한 닉네임 입니다.";

// 3) 닉네임 입력 시 마다 유효성 검사
signUpMemberNickname.addEventListener("input", () => {



  const inputmemberNickname = signUpMemberNickname.value.trim();

  // 4) 입력된 닉네임이 없을 경우
  if (inputmemberNickname.length === 0) {
    nickMessage.innerText = nickCheck.normal;
    // alert("nickMessage.classList");
    nickMessage.classList.remove("confirm", "error");
    checkObj.memberNickname = false;
    signUpMemberNickname.value = "";
    return;
  }

  // 5) 닉네임 유효성 검사(정규 표현식)
  const regEx = /^[a-zA-Z0-9가-힣]{3,10}$/; // 한글,영어,숫자로만 3~10글자

  if (regEx.test(inputmemberNickname) === false) {
    nickMessage.innerText = nickCheck.invaild;
    // alert("nickMessage.classList");
    nickMessage.classList.add("error");
    nickMessage.classList.remove("confirm");
    checkObj.memberNickname = false;
    return;
  }

  // 6) 닉네임 중복 검사
  fetch("/member/nicknameCheck?memberNickname=" + inputmemberNickname)
    .then(response => {
      if (response.ok) return response.text();
      throw new Error("닉네임 중복 검사 에러");
    })
    .then(count => {
      if (count == 1) {
        nickMessage.innerText = nickCheck.duplication;
        // alert("nickMessage.classList");
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        checkObj.memberNickname = false;
        return;
      }

      nickMessage.innerText = nickCheck.check;
      // alert("nickMessage.classList");
      nickMessage.classList.add("confirm");
      nickMessage.classList.remove("error");
      checkObj.memberNickname = true;

    })
    .catch(err => console.error(err));

});



const signUpForm = document.querySelector("#signUpForm");

signUpForm.addEventListener("submit", e => {
  // e.preventDefault(); // 기본 이벤트(form제출) 막기

  // for(let key in 객체)
  // -> 반복마다 객체의 키 값을 하나씩 꺼내서 key 변수에 저장

  // 유효성 검사 체크리스트 객체에서 하나씩 꺼내서
  // false인 경우가 있는지 검사
  for (let key in checkObj) {
    if (checkObj[key] === false) { // 유효하지 않은 경우
      let str; // 출력할 메시지 저장

      switch (key) {
        case "memberEmail": str = "이메일이 유효하지 않습니다"; break;
        case "memberNickname": str = "닉네임이 유효하지 않습니다"; break;
        case "memberPw": str = "비밀번호가 유효하지 않습니다"; break;
      }
      alert(str); // 경고 출력

      // 유효하지 않은 요소로 focus 이동
      document.getElementById(key).focus();
      e.preventDefault(); // 제출 막기

      return;
    }
  }
});


/* 주소 유효성 검사 */
// - 모두 작성 또는 모두 미작성

// const addr = document.querySelectorAll("[name = memberAddress]");

// let empty = 0; // 비어있는 input의 개수
// let notEmpty = 0; // 비어있지 않은 input의 개수

// // for ~ of 향상된 for문
// for (let item of addr) {
//   let len = item.value.trim().length; // 작성된 값의 길이

//   if (len > 0) notEmpty++; // 비어있지 않은 경우
//   else empty++;    // 비어있을 경우
// }

// // empty, notEmpty 중 3이 하나도 없을 경우
// if (empty < 3 && notEmpty < 3) {
//   alert("주소가 유효하지 않습니다(모두 작성 또는 미작성)");
//   e.preventDefault();
//   return;
// }

// });

