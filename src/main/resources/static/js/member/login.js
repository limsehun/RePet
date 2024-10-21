const login = document.querySelector("#login");
const popup = document.querySelector("#popup");
const username = document.querySelector("#username");
const password = document.querySelector("#password");
const loginBtn = document.querySelector("#loginBtn");
const signUpBtn = document.querySelector("#signUpBtn");


login.addEventListener("click", () => {

  popup.classList.remove("popup-hidden");
})