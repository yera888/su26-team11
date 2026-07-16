<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>My Profile — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/profile.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers/${provider.providerProfileId?c}">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/listings?providerId=${provider.providerProfileId?c}">My Feed</a>
      <a href="/providers/${provider.providerProfileId?c}">Profile</a>
      <a href="/listings/new?providerId=${provider.providerProfileId?c}">Add Book</a>
    </div>
  </nav>

  <main class="profile-wrap">
    <h1 class="page-title">My Profile</h1>

    <section class="profile-section">
      <div class="section-head">
        <h3>Book Lister Profile</h3>
      </div>
      <div class="summary-grid">
        <div class="summary-card">
          <h4>Full Name</h4>
          <p>${provider.account.firstName} ${provider.account.lastName}</p>
        </div>
        <div class="summary-card">
          <h4>Username</h4>
          <p>${provider.account.username}</p>
        </div>
        <div class="summary-card swap-credit-card">
          <h4 class="swap-credit-label">SWAP CREDIT</h4>
          <p class="swap-credit-value">${provider.swapCreditBalance?c}</p>
        </div>
      </div>
    </section>

    <section class="profile-section">
      <div class="section-head">
        <h3>My Listings</h3>
        <a class="btn-secondary" href="/listings/new?providerId=${provider.providerProfileId?c}">+ Add Book</a>
      </div>

      <div class="listing-grid">
        <#list provider.bookListings as book>
          <article class="listing-card">
            <div class="listing-media">
              <#if (book.imageLink)?? && book.imageLink != "">
                <img src="${book.imageLink}" alt="${book.title}" style="width:100%;height:200px;object-fit:cover;display:block;" />
              <#else>
                <span class="media-text">${book.title}</span>
              </#if>
            </div>
            <div class="listing-body">
              <span class="title">${book.title}</span>
              <span class="author">by ${book.author}</span>
              <span class="isbn">ISBN: ${book.isbn}</span>
              <span class="status-badge">${(book.status)!'Available'}</span>
              <div class="listing-tags">
                <#if book.listingTags??>
                  <#list book.listingTags as bookTag>
                    <#if bookTag.tag??><span class="pill">${bookTag.tag.tagName}</span></#if>
                  </#list>
                </#if>
              </div>
            </div>
            <div class="listing-card-actions">
              <a class="btn-small" href="/listings/${book.listingId?c}">View</a>
              <a class="btn-small danger" href="/listings/${book.listingId?c}/delete?providerId=${provider.providerProfileId?c}">Remove</a>
            </div>
          </article>
        <#else>
          <p class="empty-state">No listings yet. <a href="/listings/new?providerId=${provider.providerProfileId?c}">Add your first book</a>.</p>
        </#list>
      </div>
    </section>

    <section class="account-actions">
      <a class="btn-account danger" href="/providers/${provider.providerProfileId?c}/delete">Delete Account</a>
    </section>
  </main>
</body>
</html>
