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
</head>
<body>
  <nav class="topnav">
    <a class="brand-link" href="/providers/${providerId?c}">Swap-A-Bookaroo</a>
    <div class="nav-links">
      <a href="/listings?providerId=${providerId?c}">Book Feed</a>
      <a href="/providers/${providerId?c}">Profile</a>
      <a href="/listings/new?providerId=${providerId?c}">Add Book</a>
    </div>
  </nav>

  <main class="form-wrap">
    <h1>Add a Book</h1>
    <p class="form-subhead">List a book by pasting its cover image link and filling out the details below.</p>

    <form class="form-card" action="/listings/save?providerId=${providerId?c}" method="post">
      <div>
        <label class="label" for="title">Title</label>
        <input class="input" id="title" name="title" type="text" placeholder="Book title" required />
      </div>

      <div>
        <label class="label" for="author">Author</label>
        <input class="input" id="author" name="author" type="text" placeholder="Author name" required />
      </div>

      <div>
        <label class="label" for="isbn">ISBN</label>
        <input class="input" id="isbn" name="isbn" type="text" placeholder="978-3-16-148410-0" required />
      </div>

      <div>
        <label class="label" for="img">Cover Image Link</label>
        <input class="input" id="img" name="imageLink" type="url" placeholder="https://example.com/cover.jpg" />
      </div>

      <div>
        <label class="label">Tags (enter at least 3 genres)</label>
        <input class="input" name="tagNames" list="tagOptions" type="text" placeholder="Tag 1" required />
        <input class="input" name="tagNames" list="tagOptions" type="text" placeholder="Tag 2" required />
        <input class="input" name="tagNames" list="tagOptions" type="text" placeholder="Tag 3" required />
        <p class="tag-hint">You must enter at least 3 tags.</p>
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
        <a class="btn-secondary" href="/providers/${providerId?c}">Cancel</a>
      </div>
    </form>
  </main>
</body>
</html>