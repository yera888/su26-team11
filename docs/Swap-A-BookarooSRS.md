# Requirements - Swap-A-Bookaroo


**Project Name:** Swap-A-Bookaroo \
**Team:** Yeraldine Tamayo - Provider, Jacob McGinniss - Customer \
**Course:** CSC 340\
**Version:** 2.0\
**Date:** July 22, 2026 

---

## 1. Overview
**Vision.** Swap-a-bookaroo is a genre-based book-swapping platform designed to help readers declutter their bookshelves by trading books that match their personal preferences. The system supports customers seeking books aligned with their genre preferences, as well as providers who want to list and circulate books they no longer need. 


**Glossary** Terms used in the project

- **Customer:** A person seeking books to swap based on genre preferences.
- **Profile:** A collection of information about a user, including personal details, genre preferences, and (for providers) listing history.
- **Listing:** A book posted by a provider, including its ISBN, tags, and cover image link.
- **Tag:** A genre label (e.g., Sci-Fi, Fantasy, Romance) assigned to a book; each book has at least 3 tags.
- **Swap:** The exchange of a book between a customer and a provider, tracked through listing status changes.
- **History:** Requests whose status is COMPLETED, REJECTED, or CANCELLED.
- **Swap Request:** A customer's request for a provider's listing


**Primary Users / Roles.**
- **Customer** - Find books matching genre preferences and request swaps.
- **Provider** — List books for swap and manage active listings and transactions.


**Scope (this semester).**
- User profiles(customers & providers)
- Search/browse books by ISBN and genre tags
- Posting, editing, and removing book listings
- Personalized matched-book feed based on Swap requests and "Interested Books" list
- provider approval, rejection, cancellation, and completion actions
- customer swap requests
- Secured password storage
- request and swap history

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
    Scenario: Create a provider account
        Given I do not have an account
        And the username is not already registered
        When I submit the sign-up form with first name, last name, username, and password
        Then an account with role PROVIDER is created
        And the password is stored as a BCrypt hash
        Then I am redirected to the login page with my login credentials stored in the database

    Scenario: View my provider profile
        Given I am authenticated as a provider
        When I open /providers/me
        Then I see only the profile associated with my authenticated username


    Scenario: Delete my provider profile
        Given I am authenticated as a provider
        When I submit the delete-account form
        Then requests associated with my listings are deleted
        And my provider profile and account are deleted
        And I am logged out



    ```

- **US‑6 - Create book listings**

     _Story:_ As a provider I want to post my books by their ISBN number, three tags, and an image link.

    _Acceptance:_
    ```gherkin
    Scenario: Successfully create a listing
        Given I am logged in as a provider
        When I fill in title, author, ISBN, and at least 3 tags, an IMG and successfully submit
        Then the listing is linked to my provider profile
        And the listing status is AVAILABLE
        And the posting date is recorded
        And the listing appears in my active listing view

Scenario: Reject an invalid listing
    Given I am logged in as a provider
    When I submit fewer than three distinct nonblank tags
    Then the listing wont be created
    And I am returned to the listing form with an error indicator

    ```

- **US‑7 - Manage book listings**

     _Story:_ As a provider I want to be able to veiw, edit, or remove any active book listing.
    
    _Acceptance:_
    ```gherkin
    Scenario: View my active listings
        Given I am authenticated as a provider
        When I open /listings
        Then I see my listings whose status is AVAILABLE or REQUESTED


Scenario: Edit my listing
    Given I am logged in as a provider
    And the listing belongs to my account
    And its status is not SWAPPED or REMOVED
    When I submit the edit form
    Then the form is routed to PUT /listings/{id}
    And the title, author, ISBN, image URL, and tags are updated

Scenario: Remove my listing
    Given I am authenticated as a provider
    And the listing belongs to my account
    When I submit the remove form
    Then the form is routed to DELETE /listings/{id}
    And open requests for the listing are rejected
    And the listing status becomes REMOVED
    And the listing no longer appears in active feeds

    ```

- **US‑8 - Record listing history**

     _Story:_ As a provider I want the books I am offering to be recorded and the history of how many of my listings have been selected by customers.

    _Acceptance:_
    ```gherkin
    Scenario: View pending and completed requests from profile
        Given I am logged in as a provider
        When I open My Profile, 
        Then I see a "Requests & History" section with pending requests: to Approve the swap, and all previous finished swaps


Scenario: View requests and history
    Given I am authenticated as a provider
    When I open /providers/me
    Then I see a Requests & History section
    And I see my PENDING requests
    And I see my APPROVED requests awaiting completion
    And I see my COMPLETED, REJECTED, and CANCELLED requests
    And I see the number of completed swaps

Scenario: Approve a request
    Given a request for my listing is PENDING
    And the listing is AVAILABLE
    When I approve the request
    Then the request status becomes APPROVED
    And its response date is recorded
    And the listing status becomes REQUESTED
    And other pending requests for the same listing become REJECTED

Scenario: Reject a pending request
    Given a request for my listing is PENDING
    When I reject the request
    Then the request status becomes REJECTED
    And its response date is recorded
    And the listing remains AVAILABLE

Scenario: Cancel an approved request
    Given a request for my listing is APPROVED
    When I reject the approved request
    Then the request status becomes REJECTED
    And the listing status returns to AVAILABLE

Scenario: Complete an approved swap
    Given a request for my listing is APPROVED
    And the listing status is REQUESTED
    When I mark the swap completed
    Then the request status becomes COMPLETED
    And the completion date is recorded
    Then the listing status becomes SWAPPED
    And the completed-swap count increases in the providers profile
    ```

## 3. Non‑Functional Requirements (make them measurable)
- **Performance:** The users profile, listing, request, and feed  should complete within 2 seconds
- **Availability/Reliability:** The system should be available 99% of the time, with communicated maintenance time.
- **Security/Privacy:** No sensitive data is public by default. Methods will be used to ensure user data is safe.
- **Usability:** New users should be able to complete the registration process to find a book to swap within five minutes.
- **Traceability:** Every use case shall map to the implementation files and routes in backend_api/README.md

## 4. Assumptions, Constraints, and Policies
- The browser UI is rendered with FreeMarker.
- Hibernate schema updates are enabled for the course project.
- Authentication uses session-based form login rather than JWT.

## 5. Milestones (course‑aligned)
- **M1 Requirements** — this file + stories opened as issues.
- **M2 High‑fidelity prototype** — core customer/provider UI flows fully interactive.
- **M3 Design** — architecture, schema, API outline.
- **M4 Backend API** — key endpoints + tests.
- **M5 Increment** — ≥2 use cases end‑to‑end.
- **M6 Final** — complete system & documentation.

## 6. Change Management
- Stories are living artifacts; changes are tracked via repository issues and linked pull requests and closed with their associated milestone.
- Major changes should update this SRS.
- Changes are updated in all README.md files.
