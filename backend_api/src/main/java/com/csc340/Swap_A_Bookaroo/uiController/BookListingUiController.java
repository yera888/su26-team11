package com.csc340.Swap_A_Bookaroo.uicontroller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.service.BookListingService;

@Controller
@RequestMapping("/listings")
public class BookListingUiController {

    private final BookListingService bookListingService;

    public BookListingUiController(BookListingService bookListingService) {
        this.bookListingService = bookListingService;
    }

    // US-7: view the logged-in provider's active listings.
    @GetMapping
    public String feed(Authentication authentication, Model model) {
        model.addAttribute(
                "listings",
                bookListingService.getActiveListingsForProvider(
                        authentication.getName()));
        return "listing-feed";
    }

    // US-6: show the add-listing form.
    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("bookListing", new BookListing());
        return "add-listing";
    }

    // US-6: create a listing owned by the logged-in provider.
    @PostMapping("/save")
    public String createListing(
            Authentication authentication,
            BookListing bookListing) {
        BookListing created = bookListingService.createListingForProvider(
                authentication.getName(),
                bookListing);

        return created == null
                ? "redirect:/listings/new?error=true"
                : "redirect:/listings";
    }

    // US-7: view one owned listing.
    @GetMapping("/{id}")
    public String viewListing(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {
        model.addAttribute(
                "listing",
                requireOwnedListing(id, authentication.getName()));
        return "listing-details";
    }

    // US-7: show the edit form.
    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {
        BookListing listing = requireOwnedListing(id, authentication.getName());

        List<String> tagNames = listing.getListingTags() == null
                ? List.of()
                : listing.getListingTags().stream()
                        .filter(listingTag -> listingTag.getTag() != null)
                        .map(listingTag -> listingTag.getTag().getTagName())
                        .toList();

        listing.setTagNames(tagNames);
        model.addAttribute("listing", listing);
        return "edit-listing";
    }

    // US-7: the edit form uses _method=put and reaches this endpoint.
    @PutMapping("/{id}")
    public String updateListing(
            @PathVariable Long id,
            Authentication authentication,
            BookListing updatedListing) {
        BookListing updated = bookListingService.updateListingForProvider(
                authentication.getName(),
                id,
                updatedListing);

        return updated == null
                ? "redirect:/listings/" + id + "/edit?error=true"
                : "redirect:/listings/" + id;
    }

    // US-7: the delete form uses _method=delete and reaches this endpoint.
    @DeleteMapping("/{id}")
    public String removeListing(
            @PathVariable Long id,
            Authentication authentication) {
        boolean removed = bookListingService.removeListingForProvider(
                authentication.getName(),
                id);

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/listings";
    }

    private BookListing requireOwnedListing(Long id, String username) {
        BookListing listing =
                bookListingService.getListingForProvider(id, username);

        if (listing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return listing;
    }
}