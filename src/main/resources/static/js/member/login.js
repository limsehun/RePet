const signUpBtn = document.querySelector("#signUpBtn");
const loginBtn = document.querySelector("#loginBtn");

const lgModalBg = document.querySelector(".lgModal-bg");
const pwModalBg = document.querySelector(".pwModal-bg");

const findEmailId = document.querySelector("#find-emailId");
const findPassword = document.querySelector("#find-password");

const findEIdModalBg = document.querySelector(".findEIdModal-bg");
const findEPwModalBg = document.querySelector(".findPwdModal-bg");
findEmailId.addEventListener("click", () => {
  lgModalBg.classList.add("popup-hidden");
  findEIdModalBg.classList.remove("popup-hidden");

})



signUpBtn.addEventListener("click", () => {

  lgModalBg.classList.add("popup-hidden");
  pwModalBg.classList.remove("popup-hidden");

});

