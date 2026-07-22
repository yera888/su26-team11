package com.csc340.Swap_A_Bookaroo.apiController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.service.BookListingService;

@RestController
@RequestMapping("/api/book-listings")
public class BookListingApiController {

    private final BookListingService bookListingService;

    public BookListingApiController(BookListingService bookListingService) {
        this.bookListingService = bookListingService;
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<BookListing> getOwnedListing(
            @PathVariable Long listingId,
            Authentication authentication) {
        BookListing listing = bookListingService.getListingForProvider(
                listingId,
                authentication.getName());

        return listing != null
                ? ResponseEntity.ok(listing)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BookListing> createListing(
            Authentication authentication,
            @RequestBody BookListing listing) {
        BookListing created = bookListingService.createListingForProvider(
                authentication.getName(),
                listing);

        return created != null
                ? ResponseEntity.ok(created)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<BookListing> updateListing(
            @PathVariable Long listingId,
            Authentication authentication,
            @RequestBody BookListing updatedListing) {
        BookListing updated = bookListingService.updateListingForProvider(
                authentication.getName(),
                listingId,
                updatedListing);

        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> removeListing(
            @PathVariable Long listingId,
            Authentication authentication) {
        return bookListingService.removeListingForProvider(
                authentication.getName(),
                listingId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}