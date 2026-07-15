package com.csc340.Swap_A_Bookaroo.apiController;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BookListing> getListingById(@PathVariable Long listingId) {
        BookListing listing = bookListingService.getListingById(listingId);
        return listing != null ? ResponseEntity.ok(listing) : ResponseEntity.notFound().build();
    }

    @PostMapping("/provider/{providerProfileId}")
    public ResponseEntity<BookListing> createListing(@PathVariable Long providerProfileId, @RequestBody BookListing listing) {
        BookListing created = bookListingService.createListing(providerProfileId, listing);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<BookListing> updateListing(@PathVariable Long listingId, @RequestBody BookListing updatedListing) {
        BookListing updated = bookListingService.updateListing(listingId, updatedListing);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> removeListing(@PathVariable Long listingId) {
        return bookListingService.removeListing(listingId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}