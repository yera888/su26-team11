package com.csc340.Swap_A_Bookaroo.uiController;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.entities.Tag;
import com.csc340.Swap_A_Bookaroo.service.AccountService;
import com.csc340.Swap_A_Bookaroo.service.BookListingService;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerUiController {
    // Datafield
    private final AccountService accountService;
    private final BookListingService bookListingService;
    private final CustomerProfileService customerProfileService;
    private final ProviderProfileService providerProfileService;
    private final SwapRequestService swapRequestService;

    // Constructors
    @Autowired
    public CustomerUiController(AccountService accountService,
                                BookListingService bookListingService,
                                CustomerProfileService customerProfileService,
                                ProviderProfileService providerProfileService,
                                SwapRequestService swapRequestService) {
        this.accountService = accountService;
        this.bookListingService = bookListingService;
        this.customerProfileService = customerProfileService;
        this.providerProfileService = providerProfileService;
        this.swapRequestService = swapRequestService;
    }

    // Methods
    @GetMapping("/profile")
    public String getCustomerProfile(Model model, HttpSession session) {
        // Get the logged-in user from the session
        Account account = (Account) session.getAttribute("user");
        // If not logged in send to login
        if (account == null) {
            return "redirect:/account/login";
        }
        Long id = account.getAccountId();
        // Gets profile details
        CustomerProfile customerProfile = customerProfileService.getCustomerByAccountId(id);
        Set<Tag> preferences = null;
        // Checks to see if there are any preferences
        if (customerProfile != null) {
        preferences = customerProfile.getPreferences();
        }
        model.addAttribute("account", account);
        model.addAttribute("customerProfile", customerProfile);
        model.addAttribute("preferences", preferences);
        // Name of file location
        return "customer/profile";
    }

    @GetMapping("/preferences")
    public String updatePreferences(@RequestParam("tagName") String tagName, HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            return "redirect:/account/login";
        }
        
        Long id = account.getAccountId();
        CustomerProfile profile = customerProfileService.getCustomerByAccountId(id);
        
        if (profile != null) {
            // Find if the user already has this tag name in their preference set
            Tag existingTag = profile.getPreferences().stream()
                    .filter(t -> t.getTagName().equalsIgnoreCase(tagName))
                    .findFirst().orElse(null);

            if (existingTag != null) {
                // If they have it, break the reference link
                customerProfileService.removePreferenceTag(profile.getCustomerProfileId(), existingTag.getTagId());
            } else {
                // Otherwise, associate the master tag with this profile
                Tag newTagRef = new Tag();
                newTagRef.setTagName(tagName);
                customerProfileService.addPreferenceTag(profile.getCustomerProfileId(), newTagRef);
            }
        }
        return "redirect:/customer/profile";
    }

    @GetMapping("/feed")
    public String getMatchedBookFeed(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            return "redirect:/account/login";
        }
        
        Long id = account.getAccountId();
        CustomerProfile profile = customerProfileService.getCustomerByAccountId(id);
        if (profile != null) {
            Long customerId = profile.getCustomerProfileId();
            
            // Get the matched books
            List<BookListing> matchedBooks = customerProfileService.getMatchedBookFeed(customerId);
            
            // Push the list to the UI model
            model.addAttribute("matchedBooks", matchedBooks);
            model.addAttribute("customerProfile", profile);
        }
        return "customer/myFeed";
    }

}
