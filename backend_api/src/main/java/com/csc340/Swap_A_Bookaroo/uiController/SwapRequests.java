package com.csc340.Swap_A_Bookaroo.uiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.BookListingService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@Controller
@RequestMapping("/swap")
public class SwapRequests {

    @Autowired
    private BookListingService bookListingService;

    @Autowired
    private SwapRequestService swapRequestService;

    @GetMapping("/request-swap")
    public String showRequestSwapPage(@RequestParam("listingId") Long listingId, Model model) {
        BookListing listing = bookListingService.getListingById(listingId);

        if (listing == null) {
            return "redirect:/customer/feed";
        }

        model.addAttribute("listing", listing);
        return "customer/requestSwap";
    }

    /**
     * Handle POST form submission to create the pending request without an offer.
     */
    @PostMapping("/request-swap")
    public String requestSwap(@RequestParam("listingId") Long listingId, Authentication authentication) {
        swapRequestService.createSwapRequestForCustomer(authentication.getName(), listingId);
        return "redirect:/swap/customer/pending";
    }

    /**
     * View pending requests for logged-in customer.
     */
    @GetMapping("/customer/pending")
    public String showPendingRequests(Model model, Authentication authentication) {
        List<SwapRequest> pendingRequests = swapRequestService.getPendingRequestsForCustomer(authentication.getName());
        model.addAttribute("pendingRequests", pendingRequests);
        return "customer/pendingRequests";
    }

    /**
     * Cancel a pending request.
     */
    @PostMapping("/customer/cancel")
    public String cancelRequest(@RequestParam("requestId") Long requestId, Authentication authentication) {
        swapRequestService.rejectSwapRequestForProvider(authentication.getName(), requestId);
        return "redirect:/swap/customer/pending";
    }
}