<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Book Seeker Profile — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/profile.css" />
  <link rel="stylesheet" href="/css/tagSelect.css" />
  <link rel="stylesheet" href="/css/security-provider.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/customer/profile">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/customer/feed">My Feed</a>
      <a href="/swap/customer/pending">Swap Requests</a>
      <a href="/customer/profile">Profile</a>
      <form action="/logout" method="post" style="display: inline;">
        <button type="submit" id="logoutBtn">Log out</button>
      </form>
    </div>
  </nav>

  <main class="profile-wrap">
    <h1 class="page-title">My Profile</h1>
    <p class="page-subtitle">Manage your book seeker interests, preferences, and swap requests.</p>

    <section class="profile-section">
      <div class="section-head">
        <h3>Book Seeker Summary</h3>
      </div>
      <div class="summary-grid">
        <div class="summary-card">
          <h4>Book Seeker Name</h4>
          <p>
            <#if account?? && account.firstName??>
              ${account.firstName} ${(account.lastName)!}
            <#else>
              Book Seeker
            </#if>
          </p>
        </div>
        <div class="summary-card">
          <h4>Username</h4>
          <p>${(account.username)!'N/A'}</p>
        </div>
        <div class="summary-card">
          <h4>Preferred Tags</h4>
          <p id="preferredTagsValue">
            <#if preferences?? && preferences?has_content>
              <#list preferences as pref>${pref.tagName}<#if pref_has_next>, </#if></#list>
            <#else>
              No preferences selected
            </#if>
          </p>
        </div>
        <div class="summary-card">
          <h4>Matched Feed</h4>
          <p>View your updated book matches in your feed</p>
        </div>
      </div>
    </section>

    <section class="profile-section">
      <div class="section-head">
        <h3>Book Preferences</h3>
      </div>
      <div class="tag-grid" id="tagGrid">
        <#assign availableTags = ["Sci-Fi", "Fantasy", "Romance", "Mystery", "Thriller", "Horror", "Non-Fiction", "Biography", "Young Adult", "Classics", "Poetry", "Self-Help"]>
        <#list availableTags as tag>
          <a href="/customer/preferences?tagName=${tag}">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == tag)?has_content>selected</#if>">
              ${tag}
            </button>
          </a>
        </#list>
      </div>
      <p class="save-hint">Tap a genre to update your interests. Your matched feed reflects these choices.</p>
    </section>

    <section class="profile-section">
      <div class="section-head">
        <h3>Requests &amp; History</h3>
        <a class="btn-secondary" href="/swap/customer/pending">View All Pending</a>
      </div>

      <h4 class="subsection-title">Pending Requests</h4>
      <div class="request-list">
        <#list pendingRequests![] as request>
          <article class="request-card">
            <div>
              <strong>${(request.bookListing.title)!'Book Title'}</strong>
              <p>
                Provider: @${(request.bookListing.providerProfile.account.username)!'N/A'}
              </p>
              <p class="request-date">
                <#if request.requestDate??>
                  ${request.requestDate?string("MMM d, yyyy h:mm a")}
                </#if>
              </p>
            </div>
            <span class="history-status">${(request.status)!'PENDING'}</span>
          </article>
        <#else>
          <p class="empty-state">No pending requests.</p>
        </#list>
      </div>

      <h4 class="subsection-title">Approved — Awaiting Completion</h4>
      <div class="request-list">
        <#list approvedRequests![] as request>
          <article class="request-card">
            <div>
              <strong>${(request.bookListing.title)!'Book Title'}</strong>
              <p>Approved by @${(request.bookListing.providerProfile.account.username)!'N/A'}</p>
            </div>
            <span class="history-status">${(request.status)!'APPROVED'}</span>
          </article>
        <#else>
          <p class="empty-state">No approved swaps awaiting completion.</p>
        </#list>
      </div>

      <h4 class="subsection-title">Previous Requests</h4>
      <div class="request-list">
        <#list swapHistory![] as request>
          <article class="request-card">
            <div>
              <strong>${(request.bookListing.title)!'Book Title'}</strong>
              <p>Provider: @${(request.bookListing.providerProfile.account.username)!'N/A'}</p>
            </div>
            <span class="history-status">${(request.status)!'COMPLETED'}</span>
          </article>
        <#else>
          <p class="empty-state">No finished requests yet.</p>
        </#list>
      </div>
    </section>

    <section class="account-actions">
      <#if providerId??>
        <a class="btn-account" href="/providers/me">Switch to Book Provider Profile</a>
      <#else>
        <a class="btn-account" href="/providers/enable-provider">Become a Book Provider</a>
      </#if>
      
      <form action="/customer/delete" method="post" style="display: inline;" onsubmit="return confirm('Are you sure you want to delete your account? This cannot be undone.');">
        <button class="btn-account danger" type="submit">Delete Account</button>
      </form>
    </section>
  </main>
</body>
</html>