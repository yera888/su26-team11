<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>${listing.title} — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/profile.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers">Swap-A-Bookaroo</a>
  </nav>

  <main class="profile-wrap">
    <h1 class="page-title">${listing.title}</h1>
    <p class="page-subtitle">by ${listing.author}</p>

    <#if listing.imageLink?? && listing.imageLink != "">
      <img src="${listing.imageLink}" alt="${listing.title}" style="max-width:220px;border-radius:8px;" />
    </#if>

    <div class="summary-card">
      <p><strong>ISBN:</strong> ${listing.isbn}</p>
      <p><strong>Status:</strong> ${listing.status!''}</p>
      <p><strong>Tags:</strong>
        <#if listing.listingTags??>
          <#list listing.listingTags as bookTag>${bookTag.tag.tagName}<#sep>, </#sep></#list>
        </#if>
      </p>
    </div>

    <div class="listing-card-actions">
      <#if listing.providerProfile??>
        <a class="btn-request" href="/listings?providerId=${listing.providerProfile.providerProfileId?c}">Back to Feed</a>
        <a class="btn-secondary" href="/listings/${listing.listingId?c}/delete?providerId=${listing.providerProfile.providerProfileId?c}">Delete Listing</a>
      </#if>
    </div>
  </main>
</body>
</html>
