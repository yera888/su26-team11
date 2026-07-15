package com.csc340.Swap_A_Bookaroo.uiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.CustomerPreference;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.service.AccountService;
import com.csc340.Swap_A_Bookaroo.service.BookListingService;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

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
    @GetMapping("/profile/{id}")
    public String getCustomerProfile(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id);
        CustomerProfile customerProfile = customerProfileService.getCustomerByAccountId(id);
        CustomerPreference preferences = (CustomerPreference) customerProfile.getPreferences();
        if (account == null) {
            return "redirect:/";
        }
        model.addAttribute("account", account);
        model.addAttribute("customerProfile", customerProfile);
        model.addAttribute("preferences", preferences);
        return "profile";
    }






}
