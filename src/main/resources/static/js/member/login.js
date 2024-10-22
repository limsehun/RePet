const signUpBtn = document.querySelector("#signUpBtn");
const lgModalBg = document.querySelector(".lgModal-bg");
const pwModalBg = document.querySelector(".pwModal-bg");

signUpBtn.addEventListener("click", () => {

  lgModalBg.classList.add("popup-hidden");
  pwModalBg.classList.remove("popup-hidden");

});