package com.csc340.Swap_A_Bookaroo.uicontroller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/providers")
public class ProviderProfileUiController {

    private final ProviderProfileService providerProfileService;
    private final SwapRequestService swapRequestService;

    public ProviderProfileUiController(
            ProviderProfileService providerProfileService,
            SwapRequestService swapRequestService) {
        this.providerProfileService = providerProfileService;
        this.swapRequestService = swapRequestService;
    }

    // US-5: provider registration form.
    @GetMapping("/new")
    public String signupForm(Model model) {
        model.addAttribute("providerProfile", new ProviderProfile());
        return "provider-signup";
    }

    // US-5: passwords are BCrypt-hashed in AccountService.
    @PostMapping("/save")
    public String createProvider(ProviderProfile providerProfile) {
        ProviderProfile created =
                providerProfileService.createProviderProfile(providerProfile);

        return created == null
                ? "redirect:/providers/new?error=true"
                : "redirect:/login?registered=true";
    }

    // US-5, US-7, US-8: the authenticated provider's own profile.
    @GetMapping("/me")
    public String viewCurrentProvider(
            Authentication authentication,
            Model model) {
        String username = authentication.getName();
        ProviderProfile provider =
                providerProfileService.getProviderProfileByUsername(username);

        if (provider == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("provider", provider);
        model.addAttribute(
                "activeListings",
                providerProfileService.getActiveListingsForUsername(username));
        model.addAttribute(
                "pendingRequests",
                swapRequestService.getPendingRequestsForProvider(username));
        model.addAttribute(
                "approvedRequests",
                swapRequestService.getApprovedRequestsForProvider(username));
        model.addAttribute(
                "swapHistory",
                swapRequestService.getHistoryForProvider(username));
        model.addAttribute(
                "completedSwapCount",
                providerProfileService.getCompletedSwapCountForUsername(username));

        return "provider-profile";
    }

    // US-8: approve a pending request.
    @PutMapping("/requests/{requestId}/approve")
    public String approveRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated =
                swapRequestService.approveSwapRequestForProvider(
                        authentication.getName(),
                        requestId);

        return updated == null
                ? "redirect:/providers/me?actionError=true"
                : "redirect:/providers/me";
    }

    // US-8: reject either a pending or approved request.
    @PutMapping("/requests/{requestId}/reject")
    public String rejectRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated =
                swapRequestService.rejectSwapRequestForProvider(
                        authentication.getName(),
                        requestId);

        return updated == null
                ? "redirect:/providers/me?actionError=true"
                : "redirect:/providers/me";
    }

    // US-8: move an approved request into completed history.
    @PutMapping("/requests/{requestId}/complete")
    public String completeRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated =
                swapRequestService.completeSwapRequestForProvider(
                        authentication.getName(),
                        requestId);

        return updated == null
                ? "redirect:/providers/me?actionError=true"
                : "redirect:/providers/me";
    }

    // US-5: delete only the logged-in provider's account.
    @DeleteMapping("/me")
    public String deleteCurrentProvider(
            Authentication authentication,
            HttpServletRequest request) throws ServletException {
        boolean deleted =
                providerProfileService.deleteProviderProfileForUsername(
                        authentication.getName());

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        request.logout();
        return "redirect:/providers/new?deleted=true";
    }
}