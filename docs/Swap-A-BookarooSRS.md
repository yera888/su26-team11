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
- **US‑1 - Create & manage customer profile **
    
     _Story:_ As a customer I want to be able to create an account to post my prefrences as tags and find books based on them.
    
    _Acceptance:_
    ```gherkin
    Scenario: Register with valid credentials
        Given I am not registered
        When I provide valid registration details
        Then I should be successfully registered and logged in
    ```

- **US‑2 - Find books based on prefrences**
    
     _Story:_ As a customer I want to be able to find books that can be swaped with other users based on tags I posted as my prefrences. 

    _Acceptance:_
    ```gherkin
    Scenario: Browse swappable books 
        Given I am logged in as a customer
        When I am on my feed page
        Then I should see books that match my prefrences and that are swappable
    ```

- **US‑3 - Swap books**
    
     _Story:_ As a customer I want to be able to request a book swap from the feed of books I am presented with.
   
    _Acceptance:_
    ```gherkin
    Scenario: Swap books 
        Given I am logged in as a customer
        When I have found a book
        Then I should be able to send a swap request
    ```

- **US‑4 - Pending book swaps**
    
     _Story:_ As a customer I want to be able to veiw my curent pending book swap requets.
    
    _Acceptance:_
    ```gherkin
    Scenario: See book swap requests
        Given I have requested a book swap
        When I look for pending book swap requests
        Then I should see pending book swap requests
    ```

### 2.2 Provider Stories

- **US‑5 - Create & manage provider profile**

     _Story:_ As a provider I want to be able to create, modify, or remove my account.

    _Acceptance:_
    ```gherkin
    Scenario: Create and update provider profile
        Given I do not have a profile
        When I provide valid registration details
        Then my profile should be created
    ```

- **US‑6 - Create book listings**

     _Story:_ As a provider I want to post my books by their ISBN number, three tags, and an image link.

    _Acceptance:_
    ```gherkin
    Scenario: Create and book listings
        Given I am logged in as a provider 
        When I add a book listing
        Then the customer should see the book listing
    ```

- **US‑7 - Manage book listings**

     _Story:_ As a provider I want to be able to veiw, edit, or remove any active book listing.
    
    _Acceptance:_
    ```gherkin
    Scenario: Manage book listings
        Given I am logged in as a provider 
        When I have posted a book listing
        Then I should be able to edit the listing
    ```

- **US‑8 - Record listing history**

     _Story:_ As a provider I want the books I am offering to be recorded and the history of how many of my listings have been selected by customers.

    _Acceptance:_
    ```gherkin
    Scenario: View listing history
        Given I am logged in as a provider 
        When I on my profile page
        Then I should see the book swap requests and my past book listings
    ```

## 3. Non‑Functional Requirements (make them measurable)
- **Performance:** The system shall use less than 160 MB of user RAM.
- **Availability/Reliability:** The system should be available 99% of the time, with communicated maintenance time.
- **Security/Privacy:** No sensitive data is public by default. Methods will be used to ensure user data is safe.
- **Usability:** New users should be able to complete the registration process to find a book to swap within five minutes.

## 4. Assumptions, Constraints, and Policies
list any rules, policies, assumptions, etc.

## 5. Milestones (course‑aligned)
- **M1 Requirements** — this file + stories opened as issues.
- **M2 High‑fidelity prototype** — core customer/provider UI flows fully interactive.
- **M3 Design** — architecture, schema, API outline.
- **M4 Backend API** — key endpoints + tests.
- **M5 Increment** — ≥2 use cases end‑to‑end.
- **M6 Final** — complete system & documentation.

## 6. Change Management
- Stories are living artifacts; changes are tracked via repository issues and linked pull requests.
- Major changes should update this SRS.