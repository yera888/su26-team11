<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Edit ${listing.title} — Swap-A-Bookaroo</title>
  <link rel="stylesheet" href="/css/base.css" />
  <link rel="stylesheet" href="/css/nav.css" />
  <link rel="stylesheet" href="/css/loginPage.css" />
  <link rel="stylesheet" href="/css/listingForm.css" />
  <link rel="stylesheet" href="/css/security-provider.css" />
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers/me">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/listings">My Listings</a>
      <a href="/providers/me">Profile</a>
    </div>
  </nav>

  <main class="form-wrap">
    <h1>Edit Book Listing</h1>

    <#if RequestParameters.error??>
      <p class="form-message error">The update needs valid details and at least three tags.</p>
    </#if>

    <form class="form-card" action="/listings/${listing.listingId?c}" method="post">
      <input type="hidden" name="_method" value="put" />

      <div>
        <label class="label" for="title">Title</label>
        <input class="input" id="title" name="title" type="text" value="${listing.title}" required />
      </div>

      <div>
        <label class="label" for="author">Author</label>
        <input class="input" id="author" name="author" type="text" value="${listing.author}" required />
      </div>

      <div>
        <label class="label" for="isbn">ISBN</label>
        <input class="input" id="isbn" name="isbn" type="text" value="${listing.isbn}" required />
      </div>

      <div>
        <label class="label" for="imageLink">Cover Image Link</label>
        <input class="input" id="imageLink" name="imageLink" type="url" value="${listing.imageLink!''}" />
      </div>

      <div>
        <label class="label">Tags</label>
        <#list 0..2 as index>
          <input
            class="input"
            name="tagNames"
            type="text"
            value="<#if listing.tagNames?? && listing.tagNames?size gt index>${listing.tagNames[index]}</#if>"
            placeholder="Tag ${index + 1}"
            required />
        </#list>
        <p class="tag-hint">Keep at least three distinct tags.</p>
      </div>

      <div class="form-actions">
        <button class="btn" type="submit">Save Changes</button>
        <a class="btn-secondary" href="/listings/${listing.listingId?c}">Cancel</a>
      </div>
    </form>
  </main>
</body>
</html>
