<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Request Swap — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="../css/base.css" />
  <link rel="stylesheet" href="../css/nav.css" />
  <link rel="stylesheet" href="../css/profile.css" />
  <link rel="stylesheet" href="../css/requestOverlayCustomer.css" />
  <style>
    .request-page-wrap {
      max-width: 900px;
      margin: 2rem auto;
      padding: 0 1rem;
    }

    .request-content-full {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 2rem;
      background: #ffffff;
      padding: 2.5rem;
      border-radius: 12px;
      box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
      margin-top: 1.5rem;
    }

    .request-panel {
      flex: 1;
    }

    @media (max-width: 768px) {
      .request-content-full {
        flex-direction: column;
      }
      .arrow {
        transform: rotate(90deg);
      }
    }
  </style>
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/customer/profile">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/customer/feed">My Feed</a>
      <a href="swapRequests.html">Swap Requests</a>
      <a href="/customer/profile">Profile</a>
      <a href="/logout" style="text-decoration: none;">
        <button type="button" id="logoutBtn">Log out</button>
      </a>
    </div>
  </nav>

  <main class="request-page-wrap">
    <h1 class="page-title">Request Swap</h1>
    <p class="page-subtitle">Confirm your swap details below.</p>

    <div class="request-content-full">
      <div class="request-panel">
        <h2>Selected Book</h2>
        <div class="book-card">
          <#if listing.imageLink?? && listing.imageLink != "">
            <img src="${listing.imageLink}" alt="${listing.title!''}" class="book-cover" style="object-fit: cover;" />
          <#else>
            <div class="book-cover">${(listing.title[0])!'?'}</div>
          </#if>

          <div class="book-details">
            <h3>${listing.title!''}</h3>
            <p>by ${listing.author!''}</p>

            <#-- Build tag list string -->
            <#assign tagNamesList = []>
            <#if listing.listingTags??>
              <#list listing.listingTags as listingTag>
                <#if listingTag.tag?? && listingTag.tag.tagName??>
                  <#assign tagNamesList = tagNamesList + [listingTag.tag.tagName]>
                </#if>
              </#list>
            </#if>
            <p>${tagNamesList?join(" • ")}</p>

            <#assign ownerName = "Bookaroo User">
            <#if listing.providerProfile?? && listing.providerProfile.account??>
              <#assign ownerName = listing.providerProfile.account.username>
            </#if>
            <p>Available from ${ownerName}</p>
          </div>
        </div>

        <p class="prompt">Are you sure you want to request this book?</p>

        <form action="/api/swap-requests/listing/${listing.listingId}" method="POST">
          <div class="request-actions">
            <button class="btn" type="submit">Yes, Request Swap</button>
            <a class="btn-secondary" href="/customer/feed" style="text-decoration: none; text-align: center;">Keep Looking</a>
          </div>
        </form>
      </div>

      <div class="arrow" aria-hidden="true">→</div>

      <div class="request-panel">
        <div class="mystery-card">
          <img class="mystery-cover" src="../images/question.jpg" alt="Mystery book placeholder" />
        </div>
      </div>
    </div>
  </main>
</body>
</html>