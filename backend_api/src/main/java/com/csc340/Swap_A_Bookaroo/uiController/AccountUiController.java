package com.csc340.Swap_A_Bookaroo.uiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.service.AccountService;
import com.csc340.Swap_A_Bookaroo.service.BookListingService;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountUiController {
    // Datafield
    private final AccountService accountService;
    private final BookListingService bookListingService;
    private final CustomerProfileService customerProfileService;
    private final ProviderProfileService providerProfileService;
    private final SwapRequestService swapRequestService;

    // Constructors
    @Autowired
    public AccountUiController(AccountService accountService,
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
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account loginRequest, Model model, HttpSession session) {
        Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if(account != null){
            // If username and password is right, 
            session.setAttribute("user", account);
            return "redirect:/customer/profile";
        }else {
            // If username or password is wrong, send a error message and keep them on login
            model.addAttribute("errorMessage","Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/account/login";
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("account", new Account());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerCustomer(@ModelAttribute Account account, Model model, HttpSession session) {
        Account createdAccount = accountService.registerDualAccount(account);
        if(createdAccount != null){
            session.setAttribute("customerId", createdAccount);

            CustomerProfile customerProfile = customerProfileService.getCustomerByAccountId(createdAccount.getAccountId());
            ProviderProfile providerProfile = providerProfileService.getProviderProfileByAccountId(createdAccount.getAccountId());
            
            session.setAttribute("customerProfileId", customerProfile.getCustomerProfileId());
            session.setAttribute("providerProfileId", providerProfile.getProviderProfileId());

            return "redirect:/customer/profile";
        } else{
            // Username already exists
            model.addAttribute("errorMessage", "Username is already taken. Please try another one.");
            model.addAttribute("account", account); 
            return "signup";
        }
    }


}
