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
      <p class="subhead">Create your Provider account</p>

      <form class="form" action="/providers/save" method="post">
        <label class="label" for="first-name">First name</label>
        <input class="input" id="first-name" name="account.firstName" type="text" placeholder="First name" required />

        <label class="label" for="last-name">Last name</label>
        <input class="input" id="last-name" name="account.lastName" type="text" placeholder="Last name" required />

        <label class="label" for="signup-username">Username</label>
        <input class="input" id="signup-username" name="account.username" type="text" placeholder="Choose a username" required />

        <label class="label" for="signup-password">Password</label>
        <input class="input" id="signup-password" name="account.password" type="password" placeholder="Choose a password" required />

        <div class="actions">
          <button class="btn" type="submit">Create account</button>
        </div>
      </form>

      <div class="switch">
        <a href="/providers">View all providers</a>
      </div>
    </div>
  </div>
</body>
</html>
