package com.csc340.Swap_A_Bookaroo.uiController;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.security.CustomAccountDetailsService;
import com.csc340.Swap_A_Bookaroo.service.AccountService;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/providers")
public class ProviderProfileUiController {

    private final ProviderProfileService providerProfileService;
    private final SwapRequestService swapRequestService;
    private final CustomerProfileService customerProfileService;
    private final AccountService accountService;
    private final CustomAccountDetailsService customAccountDetailsService;

    public ProviderProfileUiController(
            ProviderProfileService providerProfileService,
            SwapRequestService swapRequestService,
            CustomerProfileService customerProfileService,
            AccountService accountService,
            CustomAccountDetailsService customAccountDetailsService) {
        this.providerProfileService = providerProfileService;
        this.swapRequestService = swapRequestService;
        this.customerProfileService = customerProfileService;
        this.accountService = accountService;
        this.customAccountDetailsService = customAccountDetailsService;
    }

    @GetMapping("/new")
    public String signupForm(Model model) {
        model.addAttribute("providerProfile", new ProviderProfile());
        return "provider-signup";
    }

    @PostMapping("/save")
    public String createProvider(ProviderProfile providerProfile) {
        ProviderProfile created = providerProfileService.createProviderProfile(providerProfile);

        return created == null
                ? "redirect:/providers/new?error=true"
                : "redirect:/login?registered=true";
    }

    @GetMapping("/me")
    public String viewCurrentProvider(
            Authentication authentication,
            Model model) {
        String username = authentication.getName();
        ProviderProfile provider = providerProfileService.getProviderProfileByUsername(username);

        if (provider == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Check if user also has a Customer Profile
        CustomerProfile customer = customerProfileService.getCustomerByUsername(username);
        if (customer != null) {
            model.addAttribute("hasCustomerProfile", true);
        }

        model.addAttribute("provider", provider);
        model.addAttribute("activeListings", providerProfileService.getActiveListingsForUsername(username));
        model.addAttribute("pendingRequests", swapRequestService.getPendingRequestsForProvider(username));
        model.addAttribute("approvedRequests", swapRequestService.getApprovedRequestsForProvider(username));
        model.addAttribute("swapHistory", swapRequestService.getHistoryForProvider(username));
        model.addAttribute("completedSwapCount", providerProfileService.getCompletedSwapCountForUsername(username));

        return "provider-profile";
    }

    @GetMapping("/enable-provider")
    public String enableProviderAccount(Authentication authentication) {
        String username = authentication.getName();
        ProviderProfile existing = providerProfileService.getProviderProfileByUsername(username);

        if (existing == null) {
            CustomerProfile customer = customerProfileService.getCustomerByUsername(username);
            if (customer != null) {
                ProviderProfile newProvider = new ProviderProfile();
                newProvider.setAccount(customer.getAccount());
                providerProfileService.createProviderProfile(newProvider);

                UserDetails updatedDetails = customAccountDetailsService.loadUserByUsername(username);

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        updatedDetails,
                        authentication.getCredentials(),
                        updatedDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }
        }

        return "redirect:/providers/me";
    }

    @PutMapping("/requests/{requestId}/approve")
    public String approveRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated = swapRequestService.approveSwapRequestForProvider(authentication.getName(), requestId);

        return updated == null ? "redirect:/providers/me?actionError=true" : "redirect:/providers/me";
    }

    @PutMapping("/requests/{requestId}/reject")
    public String rejectRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated = swapRequestService.rejectSwapRequestForProvider(authentication.getName(), requestId);

        return updated == null ? "redirect:/providers/me?actionError=true" : "redirect:/providers/me";
    }

    @PutMapping("/requests/{requestId}/complete")
    public String completeRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated = swapRequestService.completeSwapRequestForProvider(authentication.getName(), requestId);

        return updated == null ? "redirect:/providers/me?actionError=true" : "redirect:/providers/me";
    }

    // Handles Provider deletion via POST and DELETE
    @RequestMapping(value = "/me", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteCurrentProvider(
            Authentication authentication,
            HttpServletRequest request) throws ServletException {
        boolean deleted = providerProfileService.deleteProviderProfileForUsername(authentication.getName());

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        request.logout();
        return "redirect:/providers/new?deleted=true";
    }
}