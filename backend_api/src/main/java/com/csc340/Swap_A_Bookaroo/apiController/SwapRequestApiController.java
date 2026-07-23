package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@RestController
@RequestMapping("/api/swap-requests")
public class SwapRequestApiController {

    private final SwapRequestService swapRequestService;

    public SwapRequestApiController(SwapRequestService swapRequestService) {
        this.swapRequestService = swapRequestService;
    }

    // Customer ownership comes from the logged-in username, not a supplied customer ID.
    @PostMapping("/listing/{listingId}")
    public ResponseEntity<SwapRequest> createSwapRequest(
            @PathVariable Long listingId,
            Authentication authentication) {
        SwapRequest created =
                swapRequestService.createSwapRequestForCustomer(
                        authentication.getName(),
                        listingId);

        return created != null
                ? ResponseEntity.ok(created)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/customer/pending")
    public ResponseEntity<List<SwapRequest>> getPendingCustomerRequests(
            Authentication authentication) {
        return ResponseEntity.ok(
                swapRequestService.getPendingRequestsForCustomer(
                        authentication.getName()));
    }

    @GetMapping("/provider/pending")
    public ResponseEntity<List<SwapRequest>> getPendingProviderRequests(
            Authentication authentication) {
        return ResponseEntity.ok(
                swapRequestService.getPendingRequestsForProvider(
                        authentication.getName()));
    }

    @GetMapping("/provider/history")
    public ResponseEntity<List<SwapRequest>> getProviderHistory(
            Authentication authentication) {
        return ResponseEntity.ok(
                swapRequestService.getHistoryForProvider(
                        authentication.getName()));
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<SwapRequest> approveSwapRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated =
                swapRequestService.approveSwapRequestForProvider(
                        authentication.getName(),
                        requestId);

        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<SwapRequest> rejectSwapRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated =
                swapRequestService.rejectSwapRequestForProvider(
                        authentication.getName(),
                        requestId);

        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}/complete")
    public ResponseEntity<SwapRequest> completeSwapRequest(
            @PathVariable Long requestId,
            Authentication authentication) {
        SwapRequest updated =
                swapRequestService.completeSwapRequestForProvider(
                        authentication.getName(),
                        requestId);

        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }
}