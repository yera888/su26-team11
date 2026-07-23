**Project Name: Swap-a-Bookaroo**   
**Version:** 
**Date:**  
**Purpose:** 

## Actors
- **Provider P**: A user who creates and manages book listings, reviews incoming swap requests, and approves/rejects/completes book exchanges.
- **Customer C**: A user who manages reading preferences, browses personalized book feeds, requests available book listings, and tracks pending swap status.

## Use Cases
#### 1. Customer: US‑CUST‑001 — Register & manage profile
1. Customer C1 logs in for the first time and creates a profile.
2. C1 edits their profile to add preferences.
3. C1 exits.

#### 2. Customer: US‑CUST‑002 — Find books based on preferences
1. Customer C1 navigates to their personalized book feed (/customer/feed).
2. C1 views available book listings filtered and matched by their saved preferences.

#### 3. Customer: US‑CUST‑003 — Request a book swap
1. Customer C1 selects an available book listing from the feed and views the request confirmation page.
2. C1 clicks "Yes, Request Swap" to submit a swap request to the book provider.

#### 4. Customer: US‑CUST‑004 — View pending book swaps
1. Customer C1 navigates to the pending swaps page (/swap/customer/pending).
2. C1 is able to see active swap requests and their current status (PENDING, APPROVED, or REJECTED).

#### 5. Provider: US‑PROV‑005 — Create and manage provider profile
1. Provider P1 logs in for the first time and creates a profile.
2. P1 views their provider dashboard (/providers/me) to manage profile details and inspect their swap credit balance.


#### 6. Provider: US‑PROV‑006 — Create book listings
1. Provider P1 opens the new listing form (/listings/new).
2. P1 enters book details (Title, Author, Genre, Tag), and submits the listing.

#### 7. Provider: US‑PROV‑007 — Manage book listings
1. Provider P1 views their active listings at /listings/my-listings.
2. P1 updates listing details or removes an existing book listing from available inventory.

#### 8. Provider: US‑PROV‑008 — Record listing history and manage requests
1. Provider P1 navigates to incoming swap requests (/swap/provider/pending) and chooses to approve or reject a request.
2. Upon completing the exchange, P1 marks the swap as completed (COMPLETED), which updates the book status to SWAPPED and logs the transaction in the provider swap history.

## CROSS-CUTTING TEST SCENARIOS (Non-Functional Requirements)

### Performance Requirements

**Scenario P1: Discover page response time < 1.5 seconds**
- **Setup:** Server under typical load
- **Steps:**
  1. Measure response time for "Browse" page load with 5 active providers, 10+ services
  2. Repeat 10 times
- **Expected Outcome:** 95% of requests ≤ 1.5 seconds

**Scenario P2:**
- **Setup:** 
- **Steps:**
  1. x
  2. y
- **Expected Outcome:** 

### Security & Privacy Requirements

**Scenario S1:**
- **Setup:** 
- **Steps:**
  1. x
  2. y
- **Expected Outcome:** 

**Scenario S2:**
- **Setup:** 
- **Steps:**
  1. x
  2. y
- **Expected Outcome:**

### Usability Requirements

**Scenario U1: Intuitive Swap Request Flow**
- **Setup: Logged-in Customer C1 browsing the feed.** 
- **Steps:**
  1. Click "Request Swap" on a listing card.
  2. Confirm swap on the request confirmation page and click "Yes, Request Swap".
- **Expected Outcome: User is redirected directly to /swap/customer/pending with an explicit success message confirming the request was created.** 

**Scenario U2: Navigation Accessibility**
- **Setup: User accessing the application on desktop and mobile viewports.** 
- **Steps:**
  1. Navigate through profile management, feed browsing, and listing views using keyboard-only tab navigation.
  2. Inspect form elements and call-to-action buttons for visible focus states.
- **Expected Outcome: All UI interactive elements are focusable and navigable without requiring a mouse input.** 