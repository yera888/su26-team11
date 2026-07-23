<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>My Listings — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/profile.css" />
  <link rel="stylesheet" href="/css/security-provider.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers/me">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/listings">My Listings</a>
      <a href="/providers/me">Profile</a>
      <a href="/listings/new">Add Book</a>
      <form class="nav-form" action="/logout" method="post">
        <button class="nav-button" type="submit">Log Out</button>
      </form>
    </div>
  </nav>

  <main class="profile-wrap">
    <h1 class="page-title">My Listings</h1>
    <p class="page-subtitle">View, edit, or remove your active listings.</p>

    <div class="listing-grid">
      <#list listings as book>
        <article class="listing-card">
          <div class="listing-media">
            <#if book.imageLink?? && book.imageLink != "">
              <img src="${book.imageLink}" alt="${book.title}" />
            <#else>
              <span class="media-text">${book.title}</span>
            </#if>
          </div>

          <div class="listing-body">
            <span class="title">${book.title}</span>
            <span class="author">by ${book.author}</span>
            <span class="isbn">ISBN: ${book.isbn}</span>
            <span class="status-badge">${book.status}</span>

            <div class="listing-tags">
              <#if book.listingTags??>
                <#list book.listingTags as bookTag>
                  <#if bookTag.tag??>
                    <span class="pill">${bookTag.tag.tagName}</span>
                  </#if>
                </#list>
              </#if>
            </div>
          </div>

          <div class="listing-card-actions">
            <a class="btn-small" href="/listings/${book.listingId?c}">View</a>
            <a class="btn-small" href="/listings/${book.listingId?c}/edit">Edit</a>

            <form class="inline-form" action="/listings/${book.listingId?c}" method="post">
              <input type="hidden" name="_method" value="delete" />
              <button class="btn-small danger" type="submit">Remove</button>
            </form>
          </div>
        </article>
      <#else>
        <p class="empty-state">
          No active listings yet. <a href="/listings/new">Add your first book</a>.
        </p>
      </#list>
    </div>
  </main>
</body>
</html>
