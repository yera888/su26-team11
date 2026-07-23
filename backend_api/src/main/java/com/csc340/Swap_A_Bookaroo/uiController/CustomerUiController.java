package com.csc340.Swap_A_Bookaroo.uiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.security.CustomAccountDetailsService;
import com.csc340.Swap_A_Bookaroo.service.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/customer")
public class CustomerUiController {

    private final CustomerProfileService customerProfileService;
    private final ProviderProfileService providerProfileService;
    private final SwapRequestService swapRequestService;
    private final AccountService accountService;
    private final BookListingService bookListingService;
    private final CustomAccountDetailsService customAccountDetailsService;

    @Autowired
    public CustomerUiController(CustomerProfileService customerProfileService,
                                ProviderProfileService providerProfileService,
                                SwapRequestService swapRequestService,
                                AccountService accountService,
                                BookListingService bookListingService,
                                CustomAccountDetailsService customAccountDetailsService) {
        this.customerProfileService = customerProfileService;
        this.providerProfileService = providerProfileService;
        this.swapRequestService = swapRequestService;
        this.accountService = accountService;
        this.bookListingService = bookListingService;
        this.customAccountDetailsService = customAccountDetailsService;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        CustomerProfile profile = new CustomerProfile();
        profile.setAccount(new Account());
        model.addAttribute("customerProfile", profile);
        return "signup";
    }

    @PostMapping("/signup")
    public String registerCustomer(@ModelAttribute("customerProfile") CustomerProfile profile, Model model) {
        CustomerProfile created = customerProfileService.createCustomerProfile(profile);

        if (created != null) {
            return "redirect:/account/customer-login?registered=true";
        } else {
            model.addAttribute("errorMessage", "Username is already taken. Please try another one.");
            model.addAttribute("customerProfile", profile); 
            return "signup";
        }
    }

    @GetMapping("/profile")
    public String getCustomerProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        CustomerProfile customerProfile = customerProfileService.getCustomerByUsername(username);

        List<CustomerPreference> preferences = customerProfileService.getCustomerPreferencesForUsername(username);
        ProviderProfile providerProfile = providerProfileService.getProviderProfileByUsername(username);

        model.addAttribute("account", customerProfile != null ? customerProfile.getAccount() : null);
        model.addAttribute("customerProfile", customerProfile);
        model.addAttribute("preferences", preferences);

        if (providerProfile != null) {
            model.addAttribute("providerId", providerProfile.getProviderProfileId());
        }

        return "customer/profile";
    }

    @GetMapping("/enable-customer")
    public String enableCustomerAccount(Authentication authentication) {
        String username = authentication.getName();
        CustomerProfile existing = customerProfileService.getCustomerByUsername(username);

        if (existing == null) {
            ProviderProfile provider = providerProfileService.getProviderProfileByUsername(username);
            if (provider != null) {
                Account account = provider.getAccount();
                
                // Append CUSTOMER role to existing roles if not present
                String currentRole = account.getRole();
                if (currentRole == null || currentRole.isBlank()) {
                    account.setRole("CUSTOMER");
                } else if (!currentRole.contains("CUSTOMER")) {
                    account.setRole(currentRole + ",CUSTOMER");
                }
                
                CustomerProfile newCustomer = new CustomerProfile();
                newCustomer.setAccount(account);
                customerProfileService.createCustomerProfile(newCustomer);

                // Reload user security details with new authorities
                UserDetails updatedDetails = customAccountDetailsService.loadUserByUsername(username);

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        updatedDetails,
                        authentication.getCredentials(),
                        updatedDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }
        }

        return "redirect:/customer/profile";
    }

    // Handles Customer deletion via POST and DELETE
    @RequestMapping(value = {"/delete", "/me"}, method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteCurrentCustomer(
            Authentication authentication,
            HttpServletRequest request) throws ServletException {

        boolean deleted = customerProfileService.deleteCustomerProfileForUsername(authentication.getName());

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        request.logout();
        return "redirect:/account/customer-login?deleted=true";
    }

    @GetMapping("/preferences")
    public String updatePreferences(@RequestParam("tagName") String tagName, Authentication authentication) {
        String username = authentication.getName();
        
        List<CustomerPreference> preferences = customerProfileService.getCustomerPreferencesForUsername(username);

        boolean exists = preferences != null && preferences.stream()
                .anyMatch(p -> p.getTagName() != null && p.getTagName().trim().equalsIgnoreCase(tagName.trim()));

        if (exists) {
            customerProfileService.removePreferenceTagForUsername(username, tagName.trim());
        } else {
            Tag newTag = new Tag();
            newTag.setTagName(tagName.trim());
            customerProfileService.addPreferenceTagForUsername(username, newTag);
        }

        return "redirect:/customer/profile";
    }

    @GetMapping("/feed")
    public String getMatchedBookFeed(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<BookListing> matchedBooks = customerProfileService.getMatchedBookFeedForUsername(username);
        CustomerProfile profile = customerProfileService.getCustomerByUsername(username);

        model.addAttribute("matchedBooks", matchedBooks);
        model.addAttribute("customerProfile", profile);
        return "customer/myFeed";
    }

    @GetMapping("/request-swap")
    public String showRequestSwapPage(@RequestParam("listingId") Long listingId, Model model) {
        BookListing listing = bookListingService.getListingById(listingId);

        if (listing == null) {
            return "redirect:/customer/feed";
        }

        model.addAttribute("listing", listing);
        return "customer/requestSwap";
    }

    @PostMapping("/request-swap")
    public String requestSwap(@RequestParam("listingId") Long listingId, Authentication authentication) {
        swapRequestService.createSwapRequestForCustomer(authentication.getName(), listingId);
        return "redirect:/customer/feed?requested=true";
    }
}