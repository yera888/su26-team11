package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.entities.SwapRequest;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;

@RestController
@RequestMapping("/api/provider-profiles")
public class ProviderProfileApiController {

    private final ProviderProfileService providerProfileService;

    public ProviderProfileApiController(ProviderProfileService providerProfileService) {
        this.providerProfileService = providerProfileService;
    }

    @PostMapping
    public ResponseEntity<ProviderProfile> createProviderProfile(@RequestBody ProviderProfile providerProfile) {
        ProviderProfile created = providerProfileService.createProviderProfile(providerProfile);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<ProviderProfile>> getAllProviderProfiles() {
        List<ProviderProfile> providerProfiles = providerProfileService.getAllProviderProfiles();
        return ResponseEntity.ok(providerProfiles.isEmpty() ? Collections.emptyList() : providerProfiles);
    }

    @GetMapping("/{providerProfileId}")
    public ResponseEntity<ProviderProfile> getProviderProfileById(@PathVariable Long providerProfileId) {
        ProviderProfile providerProfile = providerProfileService.getProviderProfileById(providerProfileId);
        return providerProfile != null ? ResponseEntity.ok(providerProfile) : ResponseEntity.notFound().build();
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ProviderProfile> getProviderProfileByAccountId(@PathVariable Long accountId) {
        ProviderProfile providerProfile = providerProfileService.getProviderProfileByAccountId(accountId);
        return providerProfile != null ? ResponseEntity.ok(providerProfile) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{providerProfileId}")
    public ResponseEntity<Void> deleteProviderProfile(@PathVariable Long providerProfileId) {
        return providerProfileService.deleteProviderProfile(providerProfileId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{providerProfileId}/listings")
    public ResponseEntity<List<BookListing>> getActiveListings(@PathVariable Long providerProfileId) {
        List<BookListing> listings = providerProfileService.getActiveListings(providerProfileId);
        return ResponseEntity.ok(listings.isEmpty() ? Collections.emptyList() : listings);
    }

    @GetMapping("/{providerProfileId}/swap-requests/pending")
    public ResponseEntity<List<SwapRequest>> getPendingSwapRequests(@PathVariable Long providerProfileId) {
        List<SwapRequest> swapRequests = providerProfileService.getPendingSwapRequests(providerProfileId);
        return ResponseEntity.ok(swapRequests.isEmpty() ? Collections.emptyList() : swapRequests);
    }

    @GetMapping("/{providerProfileId}/swap-history")
    public ResponseEntity<List<SwapRequest>> getSwapHistory(@PathVariable Long providerProfileId) {
        List<SwapRequest> swapHistory = providerProfileService.getSwapHistory(providerProfileId);
        return ResponseEntity.ok(swapHistory.isEmpty() ? Collections.emptyList() : swapHistory);
    }
}
