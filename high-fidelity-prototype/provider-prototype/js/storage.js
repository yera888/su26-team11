const AUTH_KEY = "swab_logged_in";
const PROFILE_NAME_KEY = "swab_provider_name";

function isLoggedIn() {
  return localStorage.getItem(AUTH_KEY) === "true";
}

function login() {
  localStorage.setItem(AUTH_KEY, "true");
}

function logout() {
  localStorage.removeItem(AUTH_KEY);
}

function requireLogin(loginPath) {
  if (!isLoggedIn()) {
    window.location.href = loginPath || "loginPage.html";
  }
}

function getProviderName() {
  return localStorage.getItem(PROFILE_NAME_KEY) || "Provider Account";
}

function setProviderName(name) {
  localStorage.setItem(PROFILE_NAME_KEY, name);
}