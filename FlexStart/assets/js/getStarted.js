const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container-login");

sign_up_btn.addEventListener("click", () =>
    goToSignUp()
)

sign_in_btn.addEventListener("click", () =>
    goToSignIn()
)

goToSignUp = () => container.classList.add("sign-up-mode");

goToSignIn = () => container.classList.remove("sign-up-mode")