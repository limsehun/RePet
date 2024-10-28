// 로그인 모달창 생성
const login = document.querySelector("#login");
const registerBtn = document.querySelector("#registerBtn");
const lgModalBg = document.querySelector(".lgModal-bg");

login.addEventListener("click", () => {

  lgModalBg.classList.remove("popup-hidden");

})

registerBtn.addEventListener("click", () => {

  pwModalBg.classList.remove("popup-hidden");

})

// 아이디 찾기 모달창
// const findEmailId = document.querySelector("#find-emailId");

// const findEIdModalBg = document.querySelector(".findEIdModal-bg");

// findEmailId.addEventListener("click", () => {
//   lgModalBg.classList.add("popup-hidden");
//   findEIdModalBg.classList.remove("popup-hidden");

// });
// findEmailId.addEventListener("click", () => {
//   lgModalBg.classList.add("popup-hidden");
//   findPassword.classList.remove("popup-hidden");

// });
// 아이디 찾기 모달창 닫기
// document.querySelector("#close3").addEventListener("click", () => {

//   document.querySelector(".findEIdModal-bg")
//     .classList.add("popup-hidden");
// });





// 로그인 모달창 닫기 버튼
document.querySelector("#close").addEventListener("click", () => {

  document.querySelector(".lgModal-bg")
    .classList.add("popup-hidden");
});

// 회원가입 모달창 닫기 버튼
document.querySelector("#close2").addEventListener("click", () => {

  document.querySelector(".pwModal-bg")
    .classList.add("popup-hidden");
});



// 회원 가입 모달창 생성/ 로그인 모달창 제거
const signUpBtn = document.querySelector("#signUpBtn");
// const lgModalBg = document.querySelector(".lgModal-bg");
const pwModalBg = document.querySelector(".pwModal-bg");

signUpBtn.addEventListener("click", () => {

  lgModalBg.classList.add("popup-hidden");
  pwModalBg.classList.remove("popup-hidden");

});

const findPassword = document.querySelector("#find-password");
const findPwModalBg = document.querySelector(".findPwModal-bg");

findPassword.addEventListener("click", () => {

  lgModalBg.classList.add("popup-hidden");
  findPwModalBg.classList.remove("popup-hidden");

});


// 비밀번호 찾기 모달창 닫기 버튼
document.querySelector("#close4").addEventListener("click", () => {

  document.querySelector(".findPwModal-bg")
    .classList.add("popup-hidden");
});



const refindLink = document.querySelector(".refind-link");
const refindEmail = document.querySelector("#refind-email");

const newPwMessage = document.querySelector("#newPwMessage");

refindLink.addEventListener("click", () => {

  if(refindEmail.value.trim().length === 0){
    alert("유효한 이메일 작성 후 클릭 하세요");
    return;
  }


  fetch("/email/sendNewPw", {
    method  : "POST",
    headers : {"Content-Type" : "application/json"},
    body    : refindEmail.value

  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("이메일 발송 실패");
  })
  .then(result => {
    // 백엔드 작성 후 나머지 코드 작성 예정
    console.log(result);

  })
  .catch(err => console.error(err));

  findPwModalBg.classList.add("popup-hidden");
  lgModalBg.classList.remove("popup-hidden");
  
  alert("새로운 비밀번호가 발송 되었습니다");
})

// -------------------------main 화면 버튼들- ---------------------------------

