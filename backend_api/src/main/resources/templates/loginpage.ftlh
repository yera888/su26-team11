<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Login — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/loginPage.css" />
</head>
<body>
  <div class="logincontainer">
    <div class="loginbox">
      <h1 class="brand">Swap-A-Bookaroo</h1>
      <p class="subhead">Welcome back, Member</p>

      <form class="form" id="loginForm" action="/account/login" method="post">
        <label class="label" for="username">Username</label>
        <input class="input" id="username" name="username" type="text" placeholder="username" required />

        <label class="label" for="password">Password</label>
        <input class="input" id="password" name="password" type="password" placeholder="password" required />

        <p class="error-message" id="loginError"></p>
        <#if errorMessage??>
          <p class="error-message" id="loginError" >${errorMessage}</p>
        <#else>
          <p class="error-message" id="loginError"></p>
        </#if>

        <div class="actions">
          <button class="btn" type="submit">Continue</button>
        </div>
      </form>

      <div class="switch">
        Don't have an account?
        <a href="/account/signup" id="signupLink">Sign up</a>
      </div>

      <footer class="small">By continuing you agree to our Terms &amp; Privacy.</footer>
      <footer class="small">You can <strong>NOT</strong> list or swap books unless you have a Member account.</footer>
    </div>
  </div>

  <script src="js/storage.js"></script>
  <script>
    const redirect = new URLSearchParams(window.location.search).get("redirect");
    if (redirect) {
      document.getElementById("signupLink").href = "signup.html?redirect=" + redirect;
    }

  </script>
</body>
</html>