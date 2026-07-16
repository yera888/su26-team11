<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Sign Up — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="../static/css/base.css" />
  <link rel="stylesheet" href="../static/css/loginPage.css" />
</head>
<body>
  <div class="logincontainer">
    <div class="loginbox">
      <h1 class="brand">Swap-A-Bookaroo</h1>
      <p class="subhead">Create your Member account</p>

      <form class="form" action="/account/signup" method="post">
        <label class="label" for="first-name">First name</label>
        <input class="input" id="first-name" name="firstName" type="text" placeholder="First name" required />

        <label class="label" for="last-name">Last name</label>
        <input class="input" id="last-name" name="lastName" type="text" placeholder="Last name" required />

        <label class="label" for="signup-username">Username</label>
        <input class="input" id="signup-username" name="username" type="text" placeholder="Choose a username" required />

        <label class="label" for="signup-password">Password</label>
        <input class="input" id="signup-password" name="password" type="password" placeholder="Choose a password" required />

        <#if errorMessage??>
          <p class="error-message" id="signupError">
            ${errorMessage}
          </p>
        </#if>

        <div class="actions">
          <button class="btn" type="submit">Create account</button>
        </div>
      </form>

      <p class="hint-credentials" id="signupMessage" style="display:none;">
        Account created! This is a prototype, so log in with username: <strong>username</strong>, password: <strong>password</strong>.
      </p>

      <div class="switch">
        Already have an account?
        <a href="loginPage.html" id="loginLink">Log in</a>
      </div>

      <footer class="small">By creating an account you agree to our Terms &amp; Privacy.</footer>
      <footer class="small">You can <strong>NOT</strong> list or swap books unless you have a Member account.</footer>
    </div>
  </div>

  <script>
    const redirect = new URLSearchParams(window.location.search).get("redirect");
    if (redirect) {
      document.getElementById("loginLink").href = "loginPage.html?redirect=" + redirect;
    }
  </script>
</body>
</html>

