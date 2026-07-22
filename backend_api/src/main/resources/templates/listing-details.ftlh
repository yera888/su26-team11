<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>${listing.title} — Swap-A-Bookaroo</title>
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
    </div>
  </nav>

  <main class="profile-wrap">
    <h1 class="page-title">${listing.title}</h1>
    <p class="page-subtitle">by ${listing.author}</p>

    <#if listing.imageLink?? && listing.imageLink != "">
      <img class="details-cover" src="${listing.imageLink}" alt="${listing.title}" />
    </#if>

    <div class="summary-card details-card">
      <p><strong>ISBN:</strong> ${listing.isbn}</p>
      <p><strong>Status:</strong> ${listing.status}</p>
      <p>
        <strong>Tags:</strong>
        <#if listing.listingTags??>
          <#list listing.listingTags as bookTag>
            ${bookTag.tag.tagName}<#sep>, </#sep>
          </#list>
        </#if>
      </p>
    </div>

    <div class="listing-card-actions details-actions">
      <a class="btn-small" href="/listings">Back</a>
      <a class="btn-small" href="/listings/${listing.listingId?c}/edit">Edit</a>

      <form class="inline-form" action="/listings/${listing.listingId?c}" method="post">
        <input type="hidden" name="_method" value="delete" />
        <button class="btn-small danger" type="submit">Delete Listing</button>
      </form>
    </div>
  </main>
</body>
</html>
