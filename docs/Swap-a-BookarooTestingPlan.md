**Project Name:** Swap-A-Bookaroo \
**Version:** 1.0
**Date:** July 22, 2026
**Purpose:** Test plan verifies that Swap-A-Bookaroo for both the customer and provider use cases.

## Actors
- Provider P: Registers an account, creates and manages book listings, and processes swap requests.
- Customer C: Registers an account, manages genre preferences, browses matching books, and submits swap requests.


## Use Cases
#### 1. Customer: US‑CUST‑001 — Register & manage profile
1. Customer C1 logs in for the first time and creates a profile.
2. C1 edits their profile to add preferences.
3. C1 exits.

#### 2. Customer: US‑CUST‑002 — Find books based on preferences
1. Customer C1 navigates to their personalized book feed (/customer/feed).
2. C1 views available book listings filtered and matched by their saved preferences.

#### 5. Provider: US-PROV-005: Register & Manage Provider Profile 

##### Create the profile
1. Provider P1 opens /providers/new.
2. P1 enters a first name, last name, username, and password.
3. Service S creates an account with the PROVIDER role and creates the associated provider profile.
4. Service S stores P1’s password as a BCrypt hash.
5. P1 opens /login and logs in using the registered username and password.

##### View the new profile

1. Service S redirects P1 to /providers/me.
2. P1 views the provider dashboard.

##### Updating the profile

1. P1 updates shared account information through the authenticated account endpoint.

##### Deleting the profile

1. On the profile page P1 click on delete account.
2. P1 deletes the provider profile.



#### 6. Provider: US-PROV-006: Create Book Listings

1. Provider P1 logs in.
2. P1 opens /listings/new.
2. P1 manually enters the book title, author, and ISBN.
3. P1 may enter a web URL for the book-cover image.
4. P1 enters at least three distinct genre tags.
5. P1 submits the listing form.
6. Any listing with fewer than three distinct tags is rejected
7. Service S assigns the authenticated provider as the listing owner.
8. Listing is posted with AVAILABLE status.


#### 7. US-PROV-007: View, Modify, or Remove Book Listings

#### Viewing to then edit a listing

1. Provider P1 logs in and opens /listings.
2. P1 views one of their active listings.
3. P1 opens the listing’s edit form.
4. P1 edits the title, author, ISBN, image URL, or genre tags.
5. P1 submits the updated listing.
6. Service S verifies that the listing belongs to P1 and still contains at least three distinct tags.

#### Removing a listing

1. P1 selects the listing in their active listings they want to remove.
2. P1 removes the listing.

#### 8.  US-PROV-008: Manage Book Swap Transactions
1. Provider P1 logs in and opens /providers/me.
2. Service S displays P1’s active listings, pending requests, approved requests awaiting completion, completed or rejected history, and completed-swap count.
3. Customer C1 submits a request for one of P1’s available listings.
4. P1 approves the pending request.
5. Service S changes the request to APPROVED and the listing to REQUESTED.
6. If another customer has a pending request for the same listing, Service S rejects the competing request.
7. Approving the request changes the listing from PRENDING to APPROVED on P1s profile
8. Completing the approve request changes it to COMPLETED.

#### Rejecting a request

1. P1 rejects an approved request.
2. Service S changes the request to REJECTED and returns the listing to AVAILABLE.


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

**Scenario P1: Provider dashboard response time is less than 1.5 seconds**
- **Setup:** Run Service S with the Neon PostgreSQL database connected. Then create at least 5 provider accounts, 10 active listings, and several pending, approved, completed, and rejected swap requests.
- **Steps:**
 1. Log in as Provider P1.

 2. Measure the response time for /providers/me.

 3. Repeat the page request 10 times.


- **Expected Outcome:** 95% of requests complete within 1.5 seconds

**Scenario P2: Listing pages respond in less than 1.5 seconds**
- **Setup: Log in as Provider P1 and create at least 10 active listings with at least three tags each.** 
- **Steps:**
 1. Measure the response time for /listings.

2. Measure the response time for one listing-details page.

3. Measure the response time after submitting a valid listing update.

4. Repeat each operation 10 times.

- **Expected Outcome: At least 95% of the measured listing-page requests complete within 1.5 seconds and the correct provider-owned listing data is displayed after each operation.** 

### Security & Privacy Requirements

**Scenario S1: Protected provider routes require authentication and the provider role**
- **Setup: Create one provider account and one customer account.** 
- **Steps:**
1. Open /providers/me without logging in.

2. Open /listings without logging in.

3. Log in as the customer.

4. Attempt to open /providers/me.

5. Attempt to open /listings.


- **Expected Outcome: Unauthenticated users are redirected to /login. The customer receives an access-denied response or is directed to the 403.ftlh page.** 

**Scenario S2:Provider ownership and passwords remain protected**
- **Setup: Create Provider P1 and Provider P2. Create at least one listing for each provider.** 
- **Steps:**
1. Inspect the password values stored in the accounts table.

2. Inspect provider API responses.

3. Log in as P1.

4. Attempt to view, edit, or remove a listing owned by P2.

5. Update P1’s password and log in using the new password.

- **Expected Outcome:Stored passwords are BCrypt hashes rather than plaintext values.P1 cannot view, edit, or remove P2’s listing.**

### Usability Requirements

**Scenario U1:Provider completes registration and listing creation within five minutes**
- **Setup: Provide a new user with the application URL and valid sample book information.** 
- **Steps:**
1. Start a timer.

2. Open /providers/new.

3. Register a provider account.

4. Open /login and log in.

5. Open /listings/new.

6. Enter the title, author, ISBN, optional image URL, and three distinct genre tags.

7. Submit the listing.

8. Stop the timer when the listing appears in /listings.

- **Expected Outcome:The provider completes registration, login, and listing creation within five minutes.The newly created listing appears with the status AVAILABLE.** 

**Scenario U2:Provider processes a swap request and views its history**
- **Setup:Create Provider P1 with an available listing.Create Customer C1 and submit a pending request for P1’s listing.** 
- **Steps:**
1. Start a timer.

2. Log in as P1.

3. Open /providers/me.

4. Locate C1’s pending request.

5. Approve the request.

6. Confirm that the request appears in the approved section.

7. Complete the approved request.

8. Confirm that it appears in transaction history.

9. Stop the timer.


- **Expected Outcome:P1 can locate, approve, and complete the request. The request moves from PENDING to APPROVED and then to COMPLETED.The listing moves from AVAILABLE to REQUESTED and then to SWAPPED.The completed-swap count increases.** 
