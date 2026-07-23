<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Provider Sign Up — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/loginPage.css" />
  <link rel="stylesheet" href="/css/security-provider.css" />
</head>
<body>
  <div class="logincontainer">
    <div class="loginbox">
      <h1 class="brand">Swap-A-Bookaroo</h1>
      <p class="subhead">Create your provider account.</p>

      <#if RequestParameters.error??>
        <p class="form-message error">That username is already in use.</p>
      </#if>

      <#if RequestParameters.deleted??>
        <p class="form-message success">Your provider account was deleted.</p>
      </#if>

      <form class="form" action="/providers/save" method="post">
        <label class="label" for="first-name">First name</label>
        <input class="input" id="first-name" name="account.firstName" type="text" required />

        <label class="label" for="last-name">Last name</label>
        <input class="input" id="last-name" name="account.lastName" type="text" required />

        <label class="label" for="signup-username">Username</label>
        <input class="input" id="signup-username" name="account.username" type="text" required />

        <label class="label" for="signup-password">Password</label>
        <input class="input" id="signup-password" name="account.password" type="password" required />

        <div class="actions">
          <button class="btn" type="submit">Create Account</button>
        </div>
      </form>

      <div class="switch">
        <a href="/login">Already have an account? Log in</a>
      </div>
    </div>
  </div>
</body>
</html>
