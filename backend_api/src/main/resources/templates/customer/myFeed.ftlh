<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>My Feed — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="../css/base.css" />
  <link rel="stylesheet" href="../css/nav.css" />
  <link rel="stylesheet" href="../css/profile.css" />
  <link rel="stylesheet" href="../css/requestOverlayCustomer.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/customer/profile">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/customer/feed">My Feed</a>
      <a href="/swap/customer/pending">Swap Requests</a>
      <a href="/customer/profile">Profile</a>
      <a href="/logout" style="text-decoration: none;">
        <button type="button" id="logoutBtn">Log out</button>
      </a>
    </div>
  </nav>

  <main class="profile-wrap">
    <div class="feed-banner">
      <img class="feed-banner-img" src="../images/books.jpg" alt="Feed banner" />
    </div>

    <h1 class="page-title">My Feed</h1>
    <p class="page-subtitle">Books matched to the genres in your book seeker profile.</p>

    <div class="listing-grid">
      <#if matchedBooks?? && (matchedBooks?size > 0)>
        <#list matchedBooks as listing>
          <article class="listing-card">
            <#if listing.imageLink?? && listing.imageLink != "">
              <div class="listing-media-img" style="height: 200px; overflow: hidden; display: flex; align-items: center; justify-content: center; background: #eee;">
                <img src="${listing.imageLink}" alt="${listing.title!''}" style="width: 100%; height: 100%; object-fit: cover;" />
              </div>
            <#else>
              <div class="listing-media">
                <span class="media-text">${(listing.title[0])!'?'}</span>
              </div>
            </#if>

            <div class="listing-body">
              <span class="title">${listing.title!''}</span>
              <span class="author">by ${listing.author!''}</span>
              
              <div class="listing-tags">
                <#if listing.listingTags??>
                  <#list listing.listingTags as listingTag>
                    <#if listingTag.tag?? && listingTag.tag.tagName??>
                      <span class="pill pill-generic">${listingTag.tag.tagName}</span>
                    </#if>
                  </#list>
                </#if>
              </div>
            </div>
            <div class="listing-card-actions">
              <a class="btn-request" href="/swap/request-swap?listingId=${listing.listingId}">
                Request Swap
              </a>
            </div>
          </article>
        </#list>
      <#else>
        <div style="grid-column: 1 / -1; text-align: center; padding: 40px;">
          <h3>No books match your profile preferences yet!</h3>
          <p>Go to your <a href="/customer/profile">Profile</a> to customize your preferences.</p>
        </div>
      </#if>
    </div>
  </main>
</body>
</html>