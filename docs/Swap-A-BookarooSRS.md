# Requirements - Swap-A-Bookaroo


**Project Name:** Swap-A-Bookaroo \
**Team:** Yeraldine Tamayo - Provider, Jacob McGinniss - Customer \
**Course:** CSC 340\
**Version:** 1.0\
**Date:** 2026-06-29

---

## 1. Overview
**Vision.** Swap-a-bookaroo is a genre-based book-swapping platform designed to help readers declutter their bookshelves by trading books that match their personal preferences. The system supports customers seeking books aligned with their genre preferences, as well as providers who want to list and circulate books they no longer need. 


**Glossary** Terms used in the project

- **Customer:** A person seeking books to swap based on genre preferences.
- **Profile:** A collection of information about a user, including personal details, genre preferences, and (for providers) listing history.
- **Listing:** A book posted by a provider, including its ISBN, tags, and cover image link.
- **Tag:** A genre label (e.g., Sci-Fi, Fantasy, Romance) assigned to a book; each book has at least 3 tags.
- **Swap:** The exchange of a book between a customer and a provider, tracked through listing status changes.



**Primary Users / Roles.**
- **Customer** - Find books matching genre preferences and request swaps.
- **Provider** — List books for swap and manage active listings and transactions.


**Scope (this semester).**
- User profiles(customers & providers)
- Search/browse books by ISBN and genre tags
- Posting, editing, and removing book listings
- Personalized matched-book feed based on Swap requests and "Interested Books" list
- Basic listing status tracking (Available / Requested) and swap history

**Out of scope (deferred).**


> This document is **requirements‑level** and solution‑neutral; design decisions (UI layouts, API endpoints, schemas) are documented separately.

---

## 2. Functional Requirements (User Stories)

### 2.1 Customer Stories
- As a customer I want to be able to create an account to post my prefrences and find books based on them.
- As a customer I want to select tags based on my book prefrences on my profile.  
- As a customer I want to be able to swap books with other users based on tags I posted as my prefrences. 
- As a customer I want to be able to request a book swap from the feed of books I am presented with. 

### 2.2 Provider Stories
- As a provider I want to post my books by their ISBN number, three tags, and an image link.
- As a provider I want to be able to veiw, edit, or remove any active book listing.
- As a provider I want the books I am offering to be recorded and the history of how many of my listings have been selected by customers.
- As a provider I want to be able to create, modify, or remove my account.