<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>My Feed — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/profile.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers/${providerId?c}">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/listings?providerId=${providerId?c}">My Feed</a>
      <a href="/providers/${providerId?c}">Profile</a>
      <a href="/listings/new?providerId=${providerId?c}">Add Book</a>
    </div>
  </nav>

  <main class="profile-wrap">
    <div class="feed-banner">
      <img class="feed-banner-img" src="/images/books.jpg" alt="Feed banner" />
    </div>

    <h1 class="page-title">My Feed</h1>
    <p class="page-subtitle">All the books you have listed for swapping.</p>

    <div class="listing-grid">
      <#list listings as book>
        <article class="listing-card">
          <div class="listing-media">
            <#if book.imageLink?? && book.imageLink != "">
              <img class="feed-banner-img" src="${book.imageLink}" alt="${book.title}" style="width:100%;height:100%;object-fit:cover;" />
            <#else>
              <span class="media-text">${book.title}</span>
            </#if>
          </div>
          <div class="listing-body">
            <span class="title">${book.title}</span>
            <span class="author">by ${book.author}</span>
            <div class="listing-tags">
              <#if book.listingTags??>
                <#list book.listingTags as lt>
                  <span class="pill">${lt.tag.tagName}</span>
                </#list>
              </#if>
            </div>
          </div>
          <div class="listing-card-actions">
            <a class="btn-request" href="/listings/${book.listingId?c}">Details</a>
          </div>
        </article>
      <#else>
        <p class="empty-state">No listings yet. <a href="/listings/new?providerId=${providerId?c}">Add your first book</a>.</p>
      </#list>
    </div>
  </main>
</body>
</html>