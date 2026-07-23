<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Add Book — Swap-A-Bookaroo</title>
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
      <form class="nav-form" action="/logout" method="post">
        <button class="nav-button" type="submit">Log Out</button>
      </form>
    </div>
  </nav>

  <main class="form-wrap">
    <h1>Add a Book</h1>
    <p class="form-subhead">Enter the book information and at least three genre tags.</p>

    <#if RequestParameters.error??>
      <p class="form-message error">The listing needs valid details and at least three tags.</p>
    </#if>

    <form class="form-card" action="/listings/save" method="post">
      <div>
        <label class="label" for="title">Title</label>
        <input class="input" id="title" name="title" type="text" required />
      </div>

      <div>
        <label class="label" for="author">Author</label>
        <input class="input" id="author" name="author" type="text" required />
      </div>

      <div>
        <label class="label" for="isbn">ISBN</label>
        <input class="input" id="isbn" name="isbn" type="text" required />
      </div>

      <div>
        <label class="label" for="imageLink">Cover Image Link</label>
        <input class="input" id="imageLink" name="imageLink" type="url" />
      </div>

      <div>
        <label class="label">Tags</label>
        <input class="input" name="tagNames" list="tagOptions" type="text" placeholder="Tag 1" required />
        <input class="input" name="tagNames" list="tagOptions" type="text" placeholder="Tag 2" required />
        <input class="input" name="tagNames" list="tagOptions" type="text" placeholder="Tag 3" required />
        <p class="tag-hint">Enter at least three distinct tags.</p>

        <datalist id="tagOptions">
          <option value="Sci-Fi"></option>
          <option value="Fantasy"></option>
          <option value="Romance"></option>
          <option value="Mystery"></option>
          <option value="Thriller"></option>
          <option value="Horror"></option>
          <option value="Non-Fiction"></option>
          <option value="Biography"></option>
          <option value="Young Adult"></option>
          <option value="Classics"></option>
          <option value="Poetry"></option>
          <option value="Self-Help"></option>
        </datalist>
      </div>

      <div class="form-actions">
        <button class="btn" type="submit">Add Listing</button>
        <a class="btn-secondary" href="/listings">Cancel</a>
      </div>
    </form>
  </main>
</body>
</html>
