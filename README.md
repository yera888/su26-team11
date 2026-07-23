## Title
> Swap-a-bookaroo


## Team Members
> Jacob McGinniss


> Yeraldine Tamayo


## Description
Swap-a-bookaroo is a swap-matching platform designed to help users declutter their bookshelves by trading books based on genre preference. Each book will be assigned at least 3 tags used to describe its genre. This way it can be matched up with someone looking for books they may be interested in, or they can simply look up a specific book of interest.


## App Functions
1. Customer (Jacob McGinniss):<br>

i. Create/Modify/Remove Customer Profile
- Register for an account with login and sign up
- Edit customer profile page to select genre preferences with tags (Sci-Fi, Fantasy, Roamance etc.)
- They can delete their account

ii. View available services
- Search Local Inventory by ISBN through search bar and it will check if that book exists in the database

iii. Subscribe to available services
- Customer can request a Book Swap
- Customer can like a book and it will save in their "Interested Books" linked to their profile

iv. Browse through Matched Books
- View a personalized feed of books from the database that were at least one book tag that matches the preferences in customers profile

2. Provider (Yeraldine Tamayo):
i. Create/Modify/Remove book listings
- When the provider lists a book they will paste a image link of the book cover image
- Provider will manually fill out Title, author, ISBN
- Assign at least 3 genres (tag names) to each book
- A newly created book listing will be given the AVAILABLE status.
- The provider can only view, edit, or remove listings that belong to their own authenticated account. Listings that have already been swapped or removed can no longer be edited.

ii. Create/Modify/Remove Provider Profile
- The provider can register for an account using the provider sign-up page and log in using their username and password.
- Edit, remove provider profile page or listings
- The provider account contains, First name, Last name, Username, Password, Provider role
-The provider can modify their shared account information, including their name, username, and password, through the authenticated account API.

iii. Manage Ongoing Transactions
- View the history of all books they currently have listed on the platform and how many have been swapped giving them the option to receive a book.
- Track the status of their books to see which ones are "Available" and which ones have been "Requested" by a customer.
- Active listings can have the following statuses 
AVAILABLE: The book can currently be requested by a customer.

REQUESTED: A customer request has been approved and the swap is in progress.

IV. Swap History
- The provider can view pending swap requests, approved swap requests.

- Completed, rejected, or cancelled request history an the total number of completed swaps

- If multiple customers request the same book, approving one request automatically rejects the other pending requests for that listing.

- The provider can reject an approved request. When an approved request is rejected, the associated listing returns to AVAILABLE.

- After an approved swap has been completed, the provider can mark the request as COMPLETED. The associated listing then changes to SWAPPED, the transaction appears in the provider’s swap history, and the provider’s completed-swap count increases.

## Implementation Summary

- Provider workflow

Register at /providers/new.

Log in at /login.

Create a listing with title, author, ISBN, optional image URL, and at least three distinct tags.

View, edit, or remove owned active listings.

Open /providers/me to see pending requests, approved requests awaiting completion, completed/rejected history, and the completed-swap count.

Approve, reject, cancel, or complete requests.


## Setting up the Neon Environment 

zsh:

 read -s "SPRING_DATASOURCE_URL? Paste the full Neon JDBC URL: "

 Paste the full Neon JDBC URL: (url pasted here)

export SPRING_DATASOURCE_URL

echo

Make sure I got: TASOURCE_URL"
jdbc:postgresql://

## Booting up the application

zsh:

./mvnw spring-boot:run


## Compartmentalization of Folders

### apiController
The apiController folder contains REST controllers that return JSON responses. Provider-related classes include ProviderProfileApiController, BookListingApiController, SwapRequestApiController, and AccountApiController.

These controllers receive authenticated HTTP requests, read route parameters or request bodies, call the appropriate service, and return an HTTP response. They do not directly access the database or implement the main business rules.

### uiController
The uiController folder contains controllers for the provider’s browser-based FreeMarker pages.

ProviderProfileUiController handles provider registration, the provider dashboard, provider deletion, and provider actions for approving, rejecting, or completing swap requests.

BookListingUiController handles the pages used to create, view, edit, and remove book listings.

SecurityUiController displays the login and access-denied pages.

### service
The service folder contains the provider-side business logic.

ProviderProfileService creates and removes provider profiles, retrieves the authenticated provider’s listings and requests, and calculates the completed-swap count.

BookListingService validates that listings contain at least three distinct tags, assigns ownership to the authenticated provider, updates listing information, and marks removed listings with the REMOVED status.

SwapRequestService processes request approval, rejection, and completion. It also updates the associated listing status and rejects competing requests when one request is approved.

AccountService registers accounts, hashes passwords with BCrypt, rejects duplicate usernames, and updates account information.

### repository
The repository folder contains Spring Data JPA interfaces that communicate with Neon PostgreSQL.

ProviderProfileRepository retrieves provider profiles by account or username. BookListingRepository retrieves listings owned by the authenticated provider and filters listings by status.

SwapRequestRepository retrieves pending, approved, completed, rejected, and cancelled requests. AccountRepository performs username lookups and duplicate checks.

TagRepository and ListingTagRepository store genre tags and the relationships between tags and book listings.

### entities
The entities folder contains the JPA classes that represent the provider-side database objects.

Account stores the provider’s name, username, password hash, and role. ProviderProfile connects the provider account to its listings and swap-credit balance.

BookListing stores the book title, author, ISBN, image link, posting date, tags, provider ownership, and listing status.

Tag and ListingTag represent the genres assigned to a listing. SwapRequest connects a customer to a requested listing and stores the transaction status and timestamps.

ListingStatus and SwapRequestStatus define the allowed listing and request states.

### security
The security folder contains the provider authentication and authorization configuration.

CustomAccountDetailsService loads an account by username when a user attempts to log in.

SecurityConfig configures BCrypt, the login process, logout behavior, role-based access rules, and redirects. Routes under /providers/**, /listings/**, /api/provider-profiles/**, and /api/book-listings/** are protected so only authenticated providers can access them, except for the public provider-registration routes.

src/main/resources/templates
The src/main/resources/templates folder contains the provider FreeMarker pages.

provider-signup.ftlh displays provider registration. login.ftlh displays the shared login form. provider-profile.ftlh displays the provider dashboard, listings, requests, history, and completed-swap count.

add-listing.ftlh, edit-listing.ftlh, listing-details.ftlh, and listing-feed.ftlh provide the browser interface for managing listings. 403.ftlh displays the access-denied page.
