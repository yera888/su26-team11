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
      <a href="swapRequests.html">Swap Requests</a>
      <a href="/customer/profile">Profile</a>
      <a href="/account/logout" style="text-decoration: none;">
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
                <img src="${listing.imageLink}" alt="${listing.title}" style="width: 100%; height: 100%; object-fit: cover;" />
              </div>
            <#else>
              <div class="listing-media">
                <span class="media-text">${listing.title[0]}</span>
              </div>
            </#if>

            <div class="listing-body">
              <span class="title">${listing.title}</span>
              <span class="author">by ${listing.author}</span>
              
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

            <#assign tagNamesList = []>
            <#if listing.listingTags??>
              <#list listing.listingTags as listingTag>
                <#if listingTag.tag?? && listingTag.tag.tagName??>
                  <#assign tagNamesList = tagNamesList + [listingTag.tag.tagName]>
                </#if>
              </#list>
            </#if>
            <#assign tagString = tagNamesList?join(" • ")>

            <#assign ownerName = "Bookaroo User">
            <#if listing.providerProfile?? && listing.providerProfile.account??>
              <#assign ownerName = listing.providerProfile.account.username>
            </#if>

            <div class="listing-card-actions">
              <a class="btn-request request-trigger" href="#" 
                data-id="${listing.id!''}"
                data-title="${listing.title!''}" 
                data-author="by ${listing.author!''}" 
                data-tags="${tagString!''}" 
                data-owner="Available from ${ownerName!''}" 
                data-cover="${listing.imageLink!''}"
                data-initial="${listing.title[0]!'?'}">
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

  <div class="request-overlay" id="requestOverlay" aria-hidden="true">
    <div class="request-modal" role="dialog" aria-modal="true" aria-labelledby="requestModalTitle">
      <div class="modal-header">
        <h1 id="requestModalTitle">Request Swap</h1>
        <button class="modal-close" type="button" id="closeRequestOverlay">Close</button>
      </div>

      <div class="request-content">
        <div class="request-panel">
          <h2>Selected Book</h2>
          <div class="book-card">
            <div class="book-cover" id="requestBookCover">C</div>
            <div class="book-details">
              <h3 id="requestBookTitle">Circe</h3>
              <p id="requestBookAuthor">by Madeline Miller</p>
              <p id="requestBookMeta">Fantasy • Mythology • Classics</p>
              <p id="requestBookOwner">Available from Jordan Rivera</p>
            </div>
          </div>
          <p class="prompt">Are you sure you want to request this book?</p>
          <div class="request-actions">
            <button class="btn" type="button" id="confirmRequestBtn">Yes</button>
            <button class="btn-secondary" type="button" id="keepLookingBtn">Keep Looking</button>
          </div>
          <p class="status-message" id="requestStatus"></p>
        </div>

        <div class="arrow" aria-hidden="true">→</div>

        <div class="request-panel">
          <div class="mystery-card">
            <img class="mystery-cover" src="../../../../static//images/question.jpg" alt="Mystery book placeholder" />
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="../../static/js/storage.js"></script>
  <script>
    requireLogin("../../static/html/loginPage.html?redirect=customer");

    const requestOverlay = document.getElementById("requestOverlay");
    const closeRequestOverlay = document.getElementById("closeRequestOverlay");
    const confirmRequestBtn = document.getElementById("confirmRequestBtn");
    const keepLookingBtn = document.getElementById("keepLookingBtn");
    const requestStatus = document.getElementById("requestStatus");
    const requestBookCover = document.getElementById("requestBookCover");
    const requestBookTitle = document.getElementById("requestBookTitle");
    const requestBookAuthor = document.getElementById("requestBookAuthor");
    const requestBookMeta = document.getElementById("requestBookMeta");
    const requestBookOwner = document.getElementById("requestBookOwner");

    function openRequestOverlay(book) {
      requestBookCover.textContent = book.cover || "?";
      requestBookTitle.textContent = book.title || "Untitled";
      requestBookAuthor.textContent = book.author || "";
      requestBookMeta.textContent = book.tags || "";
      requestBookOwner.textContent = book.owner || "";
      requestStatus.textContent = "";
      requestOverlay.classList.add("visible");
      requestOverlay.setAttribute("aria-hidden", "false");
      document.body.classList.add("overlay-open");
    }

    function closeRequestOverlayModal() {
      requestOverlay.classList.remove("visible");
      requestOverlay.setAttribute("aria-hidden", "true");
      document.body.classList.remove("overlay-open");
    }

    document.querySelectorAll(".request-trigger").forEach((trigger) => {
      trigger.addEventListener("click", function (e) {
        e.preventDefault();
        openRequestOverlay({
          cover: trigger.dataset.cover || "?",
          title: trigger.dataset.title || "Book",
          author: trigger.dataset.author || "",
          tags: trigger.dataset.tags || "",
          owner: trigger.dataset.owner || ""
        });
      });
    });

    closeRequestOverlay.addEventListener("click", closeRequestOverlayModal);
    requestOverlay.addEventListener("click", function (e) {
      if (e.target === requestOverlay) {
        closeRequestOverlayModal();
      }
    });
    keepLookingBtn.addEventListener("click", closeRequestOverlayModal);
    confirmRequestBtn.addEventListener("click", function () {
      requestStatus.textContent = "Request sent!";
    });

  </script>
</body>
</html>