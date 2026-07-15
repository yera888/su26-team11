package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.Collections;
import java.util.List;
<<<<<<< HEAD

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> origin/main
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
<<<<<<< HEAD
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
=======
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
>>>>>>> origin/main
    }

    @GetMapping
    public ResponseEntity<List<ProviderProfile>> getAllProviderProfiles() {
        List<ProviderProfile> providerProfiles = providerProfileService.getAllProviderProfiles();
<<<<<<< HEAD
        if (providerProfiles.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(providerProfiles);
=======
        return ResponseEntity.ok(providerProfiles.isEmpty() ? Collections.emptyList() : providerProfiles);
>>>>>>> origin/main
    }

    @GetMapping("/{providerProfileId}")
    public ResponseEntity<ProviderProfile> getProviderProfileById(@PathVariable Long providerProfileId) {
        ProviderProfile providerProfile = providerProfileService.getProviderProfileById(providerProfileId);
<<<<<<< HEAD
        if (providerProfile != null) {
            return ResponseEntity.ok(providerProfile);
        }
        return ResponseEntity.notFound().build();
=======
        return providerProfile != null ? ResponseEntity.ok(providerProfile) : ResponseEntity.notFound().build();
>>>>>>> origin/main
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ProviderProfile> getProviderProfileByAccountId(@PathVariable Long accountId) {
        ProviderProfile providerProfile = providerProfileService.getProviderProfileByAccountId(accountId);
<<<<<<< HEAD
        if (providerProfile != null) {
            return ResponseEntity.ok(providerProfile);
        }
        return ResponseEntity.notFound().build();
=======
        return providerProfile != null ? ResponseEntity.ok(providerProfile) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{providerProfileId}")
    public ResponseEntity<ProviderProfile> updateProviderProfile(@PathVariable Long providerProfileId, @RequestBody ProviderProfile updatedProviderProfile) {
        ProviderProfile providerProfile = providerProfileService.updateProviderProfile(providerProfileId, updatedProviderProfile);
        return providerProfile != null ? ResponseEntity.ok(providerProfile) : ResponseEntity.notFound().build();
>>>>>>> origin/main
    }

    @DeleteMapping("/{providerProfileId}")
    public ResponseEntity<Void> deleteProviderProfile(@PathVariable Long providerProfileId) {
<<<<<<< HEAD
        boolean deleted = providerProfileService.deleteProviderProfile(providerProfileId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
=======
        return providerProfileService.deleteProviderProfile(providerProfileId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
>>>>>>> origin/main
    }

    @GetMapping("/{providerProfileId}/listings")
    public ResponseEntity<List<BookListing>> getActiveListings(@PathVariable Long providerProfileId) {
        List<BookListing> listings = providerProfileService.getActiveListings(providerProfileId);
<<<<<<< HEAD
        if (listings.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(listings);
=======
        return ResponseEntity.ok(listings.isEmpty() ? Collections.emptyList() : listings);
>>>>>>> origin/main
    }

    @GetMapping("/{providerProfileId}/swap-requests/pending")
    public ResponseEntity<List<SwapRequest>> getPendingSwapRequests(@PathVariable Long providerProfileId) {
        List<SwapRequest> swapRequests = providerProfileService.getPendingSwapRequests(providerProfileId);
<<<<<<< HEAD
        if (swapRequests.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(swapRequests);
=======
        return ResponseEntity.ok(swapRequests.isEmpty() ? Collections.emptyList() : swapRequests);
>>>>>>> origin/main
    }

    @GetMapping("/{providerProfileId}/swap-history")
    public ResponseEntity<List<SwapRequest>> getSwapHistory(@PathVariable Long providerProfileId) {
        List<SwapRequest> swapHistory = providerProfileService.getSwapHistory(providerProfileId);
<<<<<<< HEAD
        if (swapHistory.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(swapHistory);
    }

}


=======
        return ResponseEntity.ok(swapHistory.isEmpty() ? Collections.emptyList() : swapHistory);
    }
}
>>>>>>> origin/main
