<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Providers — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/profile.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/providers">Providers</a>
      <a href="/providers/new">Sign Up</a>
    </div>
  </nav>

  <main class="profile-wrap">
    <h1 class="page-title">All Providers</h1>
    <p class="page-subtitle">Every provider account currently stored in the database.</p>

    <div class="listing-grid">
      <#list providers as p>
        <article class="listing-card">
          <div class="listing-body">
            <span class="title">${p.account.firstName} ${p.account.lastName}</span>
            <span class="author">@${p.account.username}</span>
          </div>
          <div class="listing-card-actions">
            <a class="btn-request" href="/providers/${p.providerProfileId?c}">View Profile</a>
          </div>
        </article>
      <#else>
        <p class="empty-state">No providers yet. <a href="/providers/new">Create one</a>.</p>
      </#list>
    </div>
  </main>
</body>
</html>
