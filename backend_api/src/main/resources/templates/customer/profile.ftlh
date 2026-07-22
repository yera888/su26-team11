<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Book Seeker Profile — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="../css/base.css" />
  <link rel="stylesheet" href="../css/nav.css" />
  <link rel="stylesheet" href="../css/profile.css" />
  <link rel="stylesheet" href="../css/tagSelect.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/customer/profile">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/customer/feed">My Feed</a>
      <a href="swapRequests.html">Swap Requests</a>
      <a href="/customer/profile">Profile</a>
      <a href="/account/logout" style="text-decoration: none;">
      <button type="button" id="logoutBtn">Log out</button>
      </a>
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
          <p>${account.firstName} ${account.lastName}</p>
        </div>
        <div class="summary-card">
          <h4>Username</h4>
          <p>${account.username}</p>
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
          <h4>Next Match</h4>
          <p>2 books available</p>
        </div>
      </div>
    </section>

    <section class="profile-section">
      <div class="section-head">
        <h3>Book Preferences</h3>
      </div>
        <div class="tag-grid" id="tagGrid">
          <a href="/customer/preferences?tagName=Sci-Fi">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Sci-Fi')?has_content>selected</#if>">
              Sci-Fi
            </button>
          </a>
          <a href="/customer/preferences?tagName=Fantasy">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Fantasy')?has_content>selected</#if>">
              Fantasy
            </button>
          </a>
          <a href="/customer/preferences?tagName=Romance">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Romance')?has_content>selected</#if>">
              Romance
            </button>
          </a>
          <a href="/customer/preferences?tagName=Mystery">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Mystery')?has_content>selected</#if>">
              Mystery
            </button>
          </a>
          <a href="/customer/preferences?tagName=Thriller">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Thriller')?has_content>selected</#if>">
              Thriller
            </button>
          </a>
          <a href="/customer/preferences?tagName=Horror">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Horror')?has_content>selected</#if>">
              Horror
            </button>
          </a>
          <a href="/customer/preferences?tagName=Non-Fiction">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Non-Fiction')?has_content>selected</#if>">
              Non-Fiction
            </button>
          </a>
          <a href="/customer/preferences?tagName=Biography">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Biography')?has_content>selected</#if>">
              Biography
            </button>
          </a>
          <a href="/customer/preferences?tagName=Young Adult">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Young Adult')?has_content>selected</#if>">
              Young Adult
            </button>
          </a>
          <a href="/customer/preferences?tagName=Classics">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Classics')?has_content>selected</#if>">
              Classics
            </button>
          </a>
          <a href="/customer/preferences?tagName=Poetry">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Poetry')?has_content>selected</#if>">
              Poetry
            </button>
          </a>
          <a href="/customer/preferences?tagName=Self-Help">
            <button type="button" class="tag-chip <#if preferences?? && preferences?filter(p -> p.tagName == 'Self-Help')?has_content>selected</#if>">
              Self-Help
            </button>
          </a>
        </div>
      <p class="save-hint">Tap a genre to update your interests. Your matched feed reflects these choices.</p>
    </section>

    <section class="profile-section">
      <div class="section-head">
        <h3>Swap Requests</h3>
        <a class="btn-secondary" href="swapRequests.html">View All</a>
      </div>
      <div class="listing-grid">
        <article class="listing-card">
          <div class="listing-body">
            <span class="title">Requested</span>
            <span class="author">Dune requested from Jordan</span>
            <span class="isbn">Status: pending</span>
          </div>
          <div class="listing-card-actions">
            <a class="btn-request" href="swapRequests.html">View</a>
          </div>
        </article>
        <article class="listing-card">
          <div class="listing-body">
            <span class="title">Completed</span>
            <span class="author">The Hobbit swapped successfully</span>
            <span class="isbn">Status: finished</span>
          </div>
          <div class="listing-card-actions">
            <button class="btn-selected" type="button">Done</button>
          </div>
        </article>
      </div>
    </section>

    <section class="account-actions">
      <a class="btn-account" href="/providers/${providerId}">Open Book Lister Profile</a>
      <a href="/customer/delete" onclick="return confirm('Are you sure you want to delete your account? This cannot be undone.');">
        <button class="btn-account danger" type="button" class="btn-danger">Delete Account</button>
      </a>
    </section>
  </main>

  <script src="../provider-prototype/js/storage.js"></script>
  <script>
    requireLogin("../provider-prototype/loginPage.html?redirect=customer");
  </script>
</body>
</html>