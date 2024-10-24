
/* 다음 주소 API로 주소 검색하기 */
function findAddress() {
  new daum.Postcode({
    oncomplete: function (data) {
      var addr = ''; // 주소 변수


      if (data.userSelectedType === 'R') { 
        addr = data.roadAddress;
      } else { // 사용자가 지번 주소를 선택했을 경우(J)
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
  "email": false,
  "password": false,
  "nickname": false,
  "check-btn": false,
  "submit-btn": false,
  "authKey": false
};


/* ----- 이메일 유효성 검사 ----- */

//  이메일 유효성 검사에 필요한 요소
const checkBtn = document.querySelector("#check-btn");
const email = document.querySelector("#email");
const emailMessage = document.querySelector("#emailMessage");


const emailCheckMessage = {}; // 빈 객체
emailCheckMessage.normal = "중복 되지 않는 이메일을 입력해주세요.";
emailCheckMessage.invaild = "알맞은 이메일 형식으로 작성해 주세요.";
emailCheckMessage.duplication = "이미 사용중인 이메일 입니다.";
emailCheckMessage.check = "사용 가능한 이메일 입니다.";

// 이메일이 입력될 때 마다 유효성 검사를 수행
email.addEventListener("input", e => {


  const inputEmail = email.value.trim();


  if (inputEmail.length === 0) {


    emailMessage.innerText = emailCheckMessage.normal;


    emailMessage.classList.remove("confirm", "error");


    checkObj.email = false;

    email.value = "";

    return;
  }


  // 이메일 형식 정규 표현식 객체
  const regEx = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // 입력 값이 이메일 형식이 아닌 경우
  if (regEx.test(inputEmail) === false) {
    emailMessage.innerText = emailCheckMessage.invaild; // 유효 X 메시지
    emailMessage.classList.add("error");
    emailMessage.classList.remove("confirm");
    checkObj.email = false; 
    return;
  }

  // 6) 이메일 중복 검사(AJAX)
  fetch("/member/emailCheck?email=" + inputEmail)
    .then(response => {
      if (response.ok) { 
        return response.text()
      }

      throw new Error("이메일 중복 검사 에러");
    })
    .then(count => {
      // checkBtn.addEventListener("click", () => {
      if (count == 1) { // 중복인 경우
        emailMessage.innerText = emailCheckMessage.duplication; // 중복 메시지
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");
        checkObj.email = false;
        return;
      }

      // 중복이 아닌 경우
      emailMessage.innerText = emailCheckMessage.check; // 중복X 메시지
      emailMessage.classList.add("confirm");
      emailMessage.classList.remove("error");
      checkObj.email = true; // 유효한 이메일임을 기록

      // })

    })
    .catch(err => console.error(err));

});



const password = document.querySelector("#password");
const passwordCheck = document.querySelector("#passwordCheck");
const pwMessage = document.querySelector("#pwMessage");

const pwCheckMessage = {};
pwCheckMessage.normal = "영어,숫자,특수문자 1글자 이상, 6~20자 사이.";
pwCheckMessage.invaild = "유효하지 않은 비밀번호 형식입니다.";
pwCheckMessage.vaild = "유효한 비밀번호 형식입니다.";
pwCheckMessage.error = "비밀번호가 일치하지 않습니다.";
pwCheckMessage.check = "비밀번호가 일치 합니다.";


password.addEventListener("input", () => {

  const inputPw = password.value.trim();

  if (inputPw.length === 0) { // 비밀번호 미입력
    pwMessage.innerText = pwCheckMessage.normal;
    pwMessage.classList.remove("confirm", "error");
    checkObj.password = false;
    password.value = "";
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
    checkObj.password = false;
    return;
  }

  pwMessage.innerText = pwCheckMessage.vaild;
  pwMessage.classList.remove("error");
  pwMessage.classList.add("confirm");
  checkObj.password = true;


  // 비밀번호가 입력된 경우
  if (passwordCheck.value.trim().length > 0) {
    checkPw(); 
  }
});

/* ----- 비밀번호, 비밀번호 확인 같은지 검사하는 함수 ----- */
function checkPw() {

  // 같은 경우
  if (password.value === passwordCheck.value) {
    pwMessage.innerText = pwCheckMessage.check;
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.passwordCheck = true;
    return;
  }
  pwMessage.innerText = pwCheckMessage.error;
  pwMessage.classList.add("error");
  pwMessage.classList.remove("confirm");
  checkObj.passwordCheck = false;
}

/* ----- 비밀번호 확인이 입력 되었을 때  ----- */
passwordCheck.addEventListener("input", () => {

  // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
  if (checkObj.password === true) {
    checkPw();
    return;
  }
  // 비밀번호 input에 작성된 값이 유효하지 않은 경우
  checkObj.passwordCheck = false;
});



/* ----- 닉네임 유효성 검사 ----- */
// 1) 닉네임 유효성 검사에 사용되는 요소 얻어오기
const nickname = document.querySelector("#nickname");
const checkBtn2 = document.querySelector("#check-btn2");
const nickMessage = document.querySelector("#nickMessage");

// 2) 닉네임 관련 메시지 작성
const nickMessageObj = {};
nickMessageObj.normal = "한글,영어,숫자로만 3~10글자";
nickMessageObj.invaild = "유효하지 않은 닉네임 형식 입니다";
nickMessageObj.duplication = "이미 사용중인 닉네임 입니다.";
nickMessageObj.check = "사용 가능한 닉네임 입니다.";

// 3) 닉네임 입력 시 마다 유효성 검사
nickname.addEventListener("input", () => {



  const inputNickname = nickname.value.trim();

  // 4) 입력된 닉네임이 없을 경우
  if (inputNickname.length === 0) {
    nickMessage.innerText = nickMessageObj.normal;
    // alert("nickMessage.classList");
    nickMessage.classList.remove("confirm", "error");
    checkObj.nickname = false;
    nickname.value = "";
    return;
  }

  // 5) 닉네임 유효성 검사(정규 표현식)
  const regEx = /^[a-zA-Z0-9가-힣]{3,10}$/; // 한글,영어,숫자로만 3~10글자

  if (regEx.test(inputNickname) === false) {
    nickMessage.innerText = nickMessageObj.invaild;
    // alert("nickMessage.classList");
    nickMessage.classList.add("error");
    nickMessage.classList.remove("confirm");
    checkObj.nickname = false;
    return;
  }

  // 6) 닉네임 중복 검사
  fetch("/member/nicknameCheck?nickname=" + inputNickname)
    .then(response => {
      if (response.ok) return response.text();
      throw new Error("닉네임 중복 검사 에러submit-btn");
    })
    .then(count => {
      if (count == 1) {
        nickMessage.innerText = nickMessageObj.duplication;
        // alert("nickMessage.classList");
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        checkObj.nickname = false;
        return;
      }

      nickMessage.innerText = nickMessageObj.check;
      // alert("nickMessage.classList");
      nickMessage.classList.add("confirm");
      nickMessage.classList.remove("error");
      checkObj.nickname = true;

    })
    .catch(err => console.error(err));

});



// /* 회원 가입 form 제출 시 전체 유효성 검사 여부 확인 */
// const signUpForm = document.querySelector("#signUpForm");

// signUpForm.addEventListener("submit", e => {

//   for (let key in checkObj) {
//     if (checkObj[key] === false) { // 유효하지 않은 경우
//       let str; // 출력할 메시지 저장

//       switch (key) {
//         case "email": str = "이메일이 유효하지 않습니다"; break;
//         case "nickname": str = "닉네임이 유효하지 않습니다"; break;
//         case "password": str = "비밀번호가 유효하지 않습니다"; break;
//         case "passwordCheck": str = "비밀번호 확인이 일치하지 않습니다"; break;
//       }
//       alert(str); // 경고 출력

//       // 유효하지 않은 요소로 focus 이동
//       document.getElementById(key).focus();
//       e.preventDefault(); // 제출 막기

//       return;
//     }
//   }


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
