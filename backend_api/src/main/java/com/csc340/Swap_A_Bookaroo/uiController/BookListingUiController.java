package com.csc340.Swap_A_Bookaroo.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.service.BookListingService;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;

@Controller
@RequestMapping("/listings")
public class BookListingUiController {

    private final BookListingService bookListingService;
    private final ProviderProfileService providerProfileService;

    public BookListingUiController(BookListingService bookListingService,
                                   ProviderProfileService providerProfileService) {
        this.bookListingService = bookListingService;
        this.providerProfileService = providerProfileService;
    }

    @GetMapping                                  // US-7: a provider's feed of listings
    public String feed(@RequestParam Long providerId, Model model) {
        model.addAttribute("listings", providerProfileService.getActiveListings(providerId));
        model.addAttribute("providerId", providerId);
        return "listing-feed";
    }

    @GetMapping("/new")                           // US-6: add-listing form
    public String addForm(@RequestParam Long providerId, Model model) {
        model.addAttribute("bookListing", new BookListing());
        model.addAttribute("providerId", providerId);
        return "add-listing";
    }

    @PostMapping("/save")                          // US-6: handle add-listing
    public String createListing(@RequestParam Long providerId, BookListing bookListing) {
        bookListingService.createListing(providerId, bookListing);
        return "redirect:/listings?providerId=" + providerId;
    }

    @GetMapping("/{id}")                            // US-7: view one listing
    public String viewListing(@PathVariable Long id, Model model) {
        model.addAttribute("listing", bookListingService.getListingById(id));
        return "listing-details";
    }

    @GetMapping("/{id}/delete")                     // US-7: remove a listing
    public String removeListing(@PathVariable Long id, @RequestParam Long providerId) {
        bookListingService.removeListing(id);
        return "redirect:/listings?providerId=" + providerId;
    }
}
