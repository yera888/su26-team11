package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ProviderProfile>> getAllProviderProfiles() {
        List<ProviderProfile> providerProfiles = providerProfileService.getAllProviderProfiles();
        if (providerProfiles.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(providerProfiles);
    }

    @GetMapping("/{providerProfileId}")
    public ResponseEntity<ProviderProfile> getProviderProfileById(@PathVariable Long providerProfileId) {
        ProviderProfile providerProfile = providerProfileService.getProviderProfileById(providerProfileId);
        if (providerProfile != null) {
            return ResponseEntity.ok(providerProfile);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ProviderProfile> getProviderProfileByAccountId(@PathVariable Long accountId) {
        ProviderProfile providerProfile = providerProfileService.getProviderProfileByAccountId(accountId);
        if (providerProfile != null) {
            return ResponseEntity.ok(providerProfile);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{providerProfileId}")
    public ResponseEntity<Void> deleteProviderProfile(@PathVariable Long providerProfileId) {
        boolean deleted = providerProfileService.deleteProviderProfile(providerProfileId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{providerProfileId}/listings")
    public ResponseEntity<List<BookListing>> getActiveListings(@PathVariable Long providerProfileId) {
        List<BookListing> listings = providerProfileService.getActiveListings(providerProfileId);
        if (listings.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{providerProfileId}/swap-requests/pending")
    public ResponseEntity<List<SwapRequest>> getPendingSwapRequests(@PathVariable Long providerProfileId) {
        List<SwapRequest> swapRequests = providerProfileService.getPendingSwapRequests(providerProfileId);
        if (swapRequests.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(swapRequests);
    }

    @GetMapping("/{providerProfileId}/swap-history")
    public ResponseEntity<List<SwapRequest>> getSwapHistory(@PathVariable Long providerProfileId) {
        List<SwapRequest> swapHistory = providerProfileService.getSwapHistory(providerProfileId);
        if (swapHistory.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(swapHistory);
    }

}


