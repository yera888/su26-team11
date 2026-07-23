<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>My Profile — Swap-A-Bookaroo</title>
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
    <h1 class="page-title">My Profile</h1>

    <#if RequestParameters.actionError??>
      <p class="form-message error">
        That request could not be updated. Refresh the page and try again.
      </p>
    </#if>

    <section class="profile-section">
      <div class="section-head">
        <h3>Provider Profile</h3>
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
          <h4>Swap Credit</h4>
          <p>${provider.swapCreditBalance?c}</p>
        </div>

        <div class="summary-card">
          <h4>Completed Swaps</h4>
          <p>${completedSwapCount?c}</p>
        </div>
      </div>
    </section>

    <section class="profile-section">
      <div class="section-head">
        <h3>My Active Listings</h3>
        <a class="btn-secondary" href="/listings/new">+ Add Book</a>
      </div>

      <div class="listing-grid">
        <#list activeListings as book>
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
          <p class="empty-state">No active listings.</p>
        </#list>
      </div>
    </section>

    <section class="profile-section">
      <div class="section-head">
        <h3>Requests &amp; History</h3>
      </div>

      <h4 class="subsection-title">Pending Requests</h4>
      <div class="request-list">
        <#list pendingRequests as request>
          <article class="request-card">
            <div>
              <strong>${request.bookListing.title}</strong>
              <p>
                Requested by
                ${request.customerProfile.account.firstName}
                ${request.customerProfile.account.lastName}
                (@${request.customerProfile.account.username})
              </p>
              <p class="request-date">
                ${request.requestDate?string("MMM d, yyyy h:mm a")}
              </p>
            </div>

            <div class="request-actions">
              <form class="inline-form" action="/providers/requests/${request.requestId?c}/approve" method="post">
                <input type="hidden" name="_method" value="put" />
                <button class="btn-small" type="submit">Approve</button>
              </form>

              <form class="inline-form" action="/providers/requests/${request.requestId?c}/reject" method="post">
                <input type="hidden" name="_method" value="put" />
                <button class="btn-small danger" type="submit">Reject</button>
              </form>
            </div>
          </article>
        <#else>
          <p class="empty-state">No pending requests.</p>
        </#list>
      </div>

      <h4 class="subsection-title">Approved — Awaiting Completion</h4>
      <div class="request-list">
        <#list approvedRequests as request>
          <article class="request-card">
            <div>
              <strong>${request.bookListing.title}</strong>
              <p>Approved for @${request.customerProfile.account.username}</p>
            </div>

            <div class="request-actions">
              <form class="inline-form" action="/providers/requests/${request.requestId?c}/complete" method="post">
                <input type="hidden" name="_method" value="put" />
                <button class="btn-small" type="submit">Mark Completed</button>
              </form>

              <form class="inline-form" action="/providers/requests/${request.requestId?c}/reject" method="post">
                <input type="hidden" name="_method" value="put" />
                <button class="btn-small danger" type="submit">Cancel Swap</button>
              </form>
            </div>
          </article>
        <#else>
          <p class="empty-state">No approved swaps awaiting completion.</p>
        </#list>
      </div>

      <h4 class="subsection-title">Previous Requests</h4>
      <div class="request-list">
        <#list swapHistory as request>
          <article class="request-card">
            <div>
              <strong>${request.bookListing.title}</strong>
              <p>Customer: @${request.customerProfile.account.username}</p>
            </div>
            <span class="history-status">${request.status}</span>
          </article>
        <#else>
          <p class="empty-state">No finished requests yet.</p>
        </#list>
      </div>
    </section>

    <section class="account-actions">
      <#if customerId??>
        <a class="btn-account" href="/customer/profile">Switch to Book Seeker Profile</a>
      <#else>
        <a class="btn-account" href="/customer/enable-customer">Become a Book Seeker</a>
      </#if>

      <form action="/providers/me" method="post" onsubmit="return confirm('Are you sure you want to delete your entire account?');">
        <input type="hidden" name="_method" value="delete" />
        <button class="btn-account danger" type="submit">Delete Account</button>
      </form>
    </section>
  </main>
</body>
</html>