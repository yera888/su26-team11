<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>${provider.account.firstName}'s Profile — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/profile.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/providers">Providers</a>
      <a href="/listings?providerId=${provider.providerProfileId?c}">Book Feed</a>
      <a href="/listings/new?providerId=${provider.providerProfileId?c}">Add Book</a>
    </div>
  </nav>

  <main class="profile-wrap">
    <h1 class="page-title">${provider.account.firstName} ${provider.account.lastName}</h1>
    <p class="page-subtitle">@${provider.account.username} · Role: ${provider.account.role!''}</p>

    <div class="summary-card">
      <p><strong>Provider ID:</strong> ${provider.providerProfileId?c}</p>
      <p><strong>Swap Credit Balance:</strong> ${provider.swapCreditBalance?c}</p>
    </div>

    <div class="listing-card-actions">
      <a class="btn-request" href="/listings/new?providerId=${provider.providerProfileId?c}">Add a Book Listing</a>
      <a class="btn-request" href="/listings?providerId=${provider.providerProfileId?c}">View My Listings</a>
      <a class="btn-secondary" href="/providers/${provider.providerProfileId?c}/delete">Delete Account</a>
    </div>
  </main>
</body>
</html>