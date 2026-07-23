## Use-Cases to mvc Implementations

Provider Side: 

US-PROV-005 — Register and Manage Provider Profile
Use Case: A provider can register, log in, view their profile dashboard, update account information, and remove their provider profile.

Model

The primary model classes are Account and ProviderProfile. Account stores the provider’s first name, last name, username, BCrypt password hash, and PROVIDER role. ProviderProfile connects the account to the provider’s listings and swap-credit balance.

AccountService validates registration information, rejects duplicate usernames, hashes passwords with BCrypt, and updates account information. ProviderProfileService creates the provider profile, initializes the swap-credit balance to zero, retrieves the profile by authenticated username, and removes the profile and linked account.

The repositories used are AccountRepository, ProviderProfileRepository, BookListingRepository, and SwapRequestRepository.

View

provider-signup.ftlh displays the provider registration form.

login.ftlh displays the shared login form.

provider-profile.ftlh displays the provider dashboard, including active listings, pending requests, approved requests, transaction history, and the completed-swap count.

Controller

ProviderProfileUiController handles the browser workflow:

GET    /providers/new
POST   /providers/save
GET    /providers/me
DELETE /providers/me
The controller registers the provider, displays the authenticated provider’s dashboard, deletes the logged-in provider, and logs the provider out after deletion.

ProviderProfileApiController provides JSON profile operations:

POST   /api/provider-profiles
GET    /api/provider-profiles/me
DELETE /api/provider-profiles/me
AccountApiController provides authenticated account retrieval and modification:

GET /api/accounts/me
PUT /api/accounts/me
The account update endpoint can change the provider’s first name, last name, username, or password. The current implementation does not contain a separate browser-based provider-edit form.

MVC Flow

Provider
→ provider-signup.ftlh or provider-profile.ftlh
→ ProviderProfileUiController
→ ProviderProfileService and AccountService
→ ProviderProfileRepository and AccountRepository
→ Neon PostgreSQL
US-PROV-006 — Create Book Listings
Use Case: A provider can create a listing containing a title, author, ISBN, optional cover-image URL, and at least three distinct genre tags.

Model

The primary model classes are BookListing, ProviderProfile, Tag, ListingTag, and ListingStatus.

BookListingService finds the provider using the authenticated username, assigns that provider as the listing owner, normalizes the tag names, verifies that at least three distinct nonblank tags were entered, and stores the listing and its tag relationships.

The service removes null, blank, and duplicate tag names before counting the tags. Existing Tag records are reused, while new tags are created when necessary.

The repositories used are:

BookListingRepository
ProviderProfileRepository
TagRepository
ListingTagRepository
View

add-listing.ftlh displays the browser form used to create a listing.

The form accepts:

Title
Author
ISBN
Optional image URL
At least three distinct tag names
Controller

BookListingUiController handles the browser workflow:

GET  /listings/new
POST /listings/save
The GET route displays the form. The POST route sends the form data and authenticated username to BookListingService. A successful creation redirects to /listings; invalid data redirects back to the form with an error parameter.

BookListingApiController also supports JSON listing creation:

POST /api/book-listings
The provider identity comes from Authentication.getName() rather than a provider ID supplied by the client.

MVC Flow

Provider
→ add-listing.ftlh
→ BookListingUiController
→ BookListingService
→ BookListingRepository, TagRepository, and ListingTagRepository
→ Neon PostgreSQL
US-PROV-007 — View, Modify, or Remove Book Listings
Use Case: A provider can view, edit, or remove active listings belonging to their authenticated account.

Model

The primary model classes are BookListing, ProviderProfile, Tag, ListingTag, and ListingStatus.

BookListingService retrieves listings using both the listing ID and the authenticated provider username. This ownership check prevents one provider from viewing, editing, or removing another provider’s listing.

When editing, the service updates the title, author, ISBN, image link, and tag relationships. The edited listing must continue to contain at least three distinct tags. Listings with the status SWAPPED or REMOVED cannot be edited.

When removing a listing, the service changes its status to REMOVED instead of deleting its database record. Pending or approved requests associated with that listing are changed to REJECTED.

The repositories used are:

BookListingRepository
ProviderProfileRepository
TagRepository
ListingTagRepository
SwapRequestRepository
View

listing-feed.ftlh displays the provider’s active listings.

listing-details.ftlh displays one provider-owned listing.

edit-listing.ftlh displays the form used to modify a listing.

provider-profile.ftlh also includes the provider’s active listings on the main dashboard.

Controller

BookListingUiController handles the browser routes:

GET    /listings
GET    /listings/{id}
GET    /listings/{id}/edit
PUT    /listings/{id}
DELETE /listings/{id}
The controller retrieves only listings owned by the authenticated provider. It converts the stored ListingTag relationships into tag-name values before displaying the edit form.

The DELETE route calls removeListingForProvider and redirects the provider back to the active-listing page.

BookListingApiController provides the equivalent JSON operations:

GET    /api/book-listings/{listingId}
PUT    /api/book-listings/{listingId}
DELETE /api/book-listings/{listingId}
MVC Flow

Provider
→ listing-feed.ftlh, listing-details.ftlh, or edit-listing.ftlh
→ BookListingUiController
→ BookListingService
→ BookListingRepository, ListingTagRepository, and SwapRequestRepository
→ Neon PostgreSQL
US-PROV-008 — Manage Ongoing Transactions and Swap History
Use Case: A provider can view pending and approved requests, approve or reject requests, complete approved swaps, view transaction history, and see the completed-swap count.

Model

The primary model classes are SwapRequest, SwapRequestStatus, BookListing, ListingStatus, ProviderProfile, and CustomerProfile.

ProviderProfileService retrieves active listings and calculates the provider’s completed-swap count. Active listings include listings with the status AVAILABLE or REQUESTED.

SwapRequestService retrieves the authenticated provider’s pending requests, approved requests, and transaction history. History includes requests with the status COMPLETED, REJECTED, or CANCELLED.

Before changing a request, the service verifies that the request belongs to a listing owned by the authenticated provider.

Approving a request changes it from PENDING to APPROVED and changes the listing from AVAILABLE to REQUESTED. Other pending requests for the same listing are automatically rejected.

Rejecting an approved request changes it to REJECTED and restores the listing to AVAILABLE. Completing an approved request changes it to COMPLETED and changes the listing to SWAPPED.

The repositories used are:

SwapRequestRepository
BookListingRepository
ProviderProfileRepository
View

provider-profile.ftlh displays:

Active listings
Pending requests
Approved requests awaiting completion
Completed and rejected history
Completed-swap count
Controller

ProviderProfileUiController loads the dashboard information through:

GET /providers/me
It places the provider, active listings, pending requests, approved requests, history, and completed-swap count into the page model.

The browser request actions are:

PUT /providers/requests/{requestId}/approve
PUT /providers/requests/{requestId}/reject
PUT /providers/requests/{requestId}/complete
SwapRequestApiController provides corresponding JSON operations:

GET /api/swap-requests/provider/pending
GET /api/swap-requests/provider/history

PUT /api/swap-requests/{requestId}/approve
PUT /api/swap-requests/{requestId}/reject
PUT /api/swap-requests/{requestId}/complete
The provider cannot currently initiate a cancellation. CANCELLED is included as a historical status, but the current controllers expose only approve, reject, and complete actions.

MVC Flow

Provider
→ provider-profile.ftlh
→ ProviderProfileUiController
→ SwapRequestService and ProviderProfileService
→ SwapRequestRepository and BookListingRepository
→ Neon PostgreSQL
