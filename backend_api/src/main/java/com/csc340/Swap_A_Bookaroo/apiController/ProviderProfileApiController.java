package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;

@RestController
@RequestMapping("/api/provider-profiles")
public class ProviderProfileApiController {

    private final ProviderProfileService providerProfileService;

    public ProviderProfileApiController(
            ProviderProfileService providerProfileService) {
        this.providerProfileService = providerProfileService;
    }

    // Public in SecurityConfig so a new provider can register.
    @PostMapping
    public ResponseEntity<ProviderProfile> createProviderProfile(
            @RequestBody ProviderProfile providerProfile) {
        ProviderProfile created =
                providerProfileService.createProviderProfile(providerProfile);

        return created != null
                ? ResponseEntity.ok(created)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ProviderProfile> getCurrentProvider(
            Authentication authentication) {
        ProviderProfile provider =
                providerProfileService.getProviderProfileByUsername(
                        authentication.getName());

        return provider != null
                ? ResponseEntity.ok(provider)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentProvider(
            Authentication authentication) {
        return providerProfileService.deleteProviderProfileForUsername(
                authentication.getName())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/me/listings")
    public ResponseEntity<List<BookListing>> getActiveListings(
            Authentication authentication) {
        return ResponseEntity.ok(
                providerProfileService.getActiveListingsForUsername(
                        authentication.getName()));
    }

    @GetMapping("/me/swap-requests/pending")
    public ResponseEntity<List<SwapRequest>> getPendingSwapRequests(
            Authentication authentication) {
        return ResponseEntity.ok(
                providerProfileService.getPendingSwapRequestsForUsername(
                        authentication.getName()));
    }

    @GetMapping("/me/swap-requests/approved")
    public ResponseEntity<List<SwapRequest>> getApprovedSwapRequests(
            Authentication authentication) {
        return ResponseEntity.ok(
                providerProfileService.getApprovedSwapRequestsForUsername(
                        authentication.getName()));
    }

    @GetMapping("/me/swap-history")
    public ResponseEntity<List<SwapRequest>> getSwapHistory(
            Authentication authentication) {
        return ResponseEntity.ok(
                providerProfileService.getSwapHistoryForUsername(
                        authentication.getName()));
    }
}