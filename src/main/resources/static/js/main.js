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