package com.csc340.Swap_A_Bookaroo.apiController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (listing != null) {
            return ResponseEntity.ok(listing);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/provider/{providerProfileId}")
    public ResponseEntity<BookListing> createListing(@PathVariable Long providerProfileId,
            @RequestBody BookListing listing) {
        BookListing created = bookListingService.createListing(providerProfileId, listing);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<BookListing> updateListing(@PathVariable Long listingId,
            @RequestBody BookListing updatedListing) {
        BookListing updated = bookListingService.updateListing(listingId, updatedListing);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> removeListing(@PathVariable Long listingId) {
        boolean removed = bookListingService.removeListing(listingId);
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
