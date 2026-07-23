<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Sign Up — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/loginPage.css" />
</head>
<body>
  <div class="logincontainer">
    <div class="loginbox">
      <h1 class="brand">Swap-A-Bookaroo</h1>
      <p class="subhead">Create your Customer account</p>

      <form class="form" action="/customer/signup" method="post">
        
        <label class="label" for="first-name">First name</label>
        <input class="input" id="first-name" name="account.firstName" type="text" placeholder="First name" required />

        <label class="label" for="last-name">Last name</label>
        <input class="input" id="last-name" name="account.lastName" type="text" placeholder="Last name" required />

        <label class="label" for="signup-username">Username</label>
        <input class="input" id="signup-username" name="account.username" type="text" placeholder="Choose a username" required />

        <label class="label" for="signup-password">Password</label>
        <input class="input" id="signup-password" name="account.password" type="password" placeholder="Choose a password" required />

        <#if errorMessage??>
          <p class="form-message error" id="signupError">
            ${errorMessage}
          </p>
        </#if>

        <div class="actions">
          <button class="btn" type="submit">Create account</button>
        </div>
      </form>

      <div class="switch">
        Already have an account?
        <a href="/account/customer-login" id="loginLink">Log in</a>
      </div>

      <footer class="small">By creating an account you agree to our Terms &amp; Privacy.</footer>
      <footer class="small">You can <strong>NOT</strong> request or swap books unless you have a Customer account.</footer>
    </div>
  </div>
</body>
</html>