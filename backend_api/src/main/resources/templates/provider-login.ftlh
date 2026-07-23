<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Provider Log In — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/loginPage.css" />
</head>
<body>
  <div class="logincontainer">
    <div class="loginbox">
      <h1 class="brand">Swap-A-Bookaroo</h1>
      <p class="subhead">Provider Portal — Log in to manage your listings.</p>

      <#if RequestParameters.registered??>
        <p class="form-message success">Account created. You can now log in.</p>
      </#if>

      <#if RequestParameters.error??>
        <p class="form-message error">The username or password was incorrect.</p>
      </#if>

      <#if RequestParameters.logout??>
        <p class="form-message success">You have been logged out.</p>
      </#if>

      <form class="form" action="/login" method="post">
        <label class="label" for="username">Username</label>
        <input class="input" id="username" name="username" type="text" required placeholder="Enter provider username" />

        <label class="label" for="password">Password</label>
        <input class="input" id="password" name="password" type="password" required placeholder="Enter password" />

        <div class="actions">
          <button class="btn" type="submit">Log In as Provider</button>
        </div>
      </form>

      <div class="switch">
        <p style="margin-bottom: 0.5rem;">
          <a href="/account/customer-login">Log in as Customer</a>
        </p>
        <a href="/providers/new">Create a provider account</a>
      </div>
    </div>
  </div>
</body>
</html>