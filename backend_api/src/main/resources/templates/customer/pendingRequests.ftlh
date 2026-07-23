<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Swap Requests — Swap-A-Bookaroo</title>
    <link rel="stylesheet" href="/css/base.css" />
    <link rel="stylesheet" href="/css/nav.css" />
    <link rel="stylesheet" href="/css/profile.css" />
    <link rel="stylesheet" href="/css/requestOverlayCustomer.css" />
</head>
<body>
    <nav class="topnav">
        <a class="brand-link" href="/customer/profile">Swap-A-Bookaroo</a>
        <div class="nav-links">
            <a href="/customer/feed">My Feed</a>
            <a href="/swap/customer/pending">Swap Requests</a>
            <a href="/customer/profile">Profile</a>
            <a href="/logout" style="text-decoration: none;">
                <button type="button" id="logoutBtn">Log out</button>
            </a>
        </div>
    </nav>

    <main class="profile-wrap">
        <h1 class="page-title">Requested Books</h1>
        <p class="page-subtitle">Books you've requested that are currently awaiting provider approval.</p>

        <div class="listing-grid">
            <#if pendingRequests?? && (pendingRequests?size > 0)>
                <#list pendingRequests as req>
                    <article class="listing-card">
                        <div class="listing-media">
                            <#if req.bookListing.imageLink?? && req.bookListing.imageLink != "">
                                <img src="${req.bookListing.imageLink}" alt="${req.bookListing.title!''}" style="width: 100%; height: 100%; object-fit: cover;" />
                            <#else>
                                <span class="media-text">${(req.bookListing.title[0])!'?'}</span>
                            </#if>
                        </div>
                        <div class="listing-body">
                            <span class="title">${req.bookListing.title!''}</span>
                            <span class="author">by ${req.bookListing.author!''}</span>
                            
                            <div class="listing-tags" style="margin-top: 0.5rem;">
                                <#if req.bookListing.listingTags??>
                                    <#list req.bookListing.listingTags as listingTag>
                                        <#if listingTag.tag?? && listingTag.tag.tagName??>
                                            <span class="pill">${listingTag.tag.tagName}</span>
                                        </#if>
                                    </#list>
                                </#if>
                            </div>
                        </div>

                        <div class="listing-card-actions" style="padding: 1rem;">
                            <form action="/swap/customer/cancel" method="POST">
                                <input type="hidden" name="requestId" value="${req.requestId}" />
                                <button type="submit" class="btn-secondary" style="width: 100%; cursor: pointer;">Cancel Request</button>
                            </form>
                        </div>
                    </article>
                </#list>
            <#else>
                <p>You have no active pending requests right now.</p>
            </#if>
        </div>
    </main>
</body>
</html>