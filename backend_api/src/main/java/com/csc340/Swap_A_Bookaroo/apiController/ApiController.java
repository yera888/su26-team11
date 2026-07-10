package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;

public class ApiController {

    @PostMapping("/api/customer-profiles")
    public ResponseEntity<CustomerProfile> createCustomerProfile(@RequestBody CustomerProfile customerProfile) {
        // Logic to create a new customer profile
        CustomerProfile createdProfile = customerProfileService.createCustomerProfile(customerProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @GetMapping("/api/customer-profiles")
    public ResponseEntity<List<CustomerProfile>> getAllCustomerProfiles() {
        // Logic to retrieve all customer profiles
        List<CustomerProfile> customerProfiles = customerProfileService.getAllCustomerProfiles();
        return ResponseEntity.ok(customerProfiles);
    }

    @GetMapping("/api/customer-profiles/{customerProfileId}")
    public ResponseEntity<CustomerProfile> getCustomerProfileById(@PathVariable Long customerProfileId) {
        // Logic to retrieve a customer profile by ID
        CustomerProfile customerProfile = customerProfileService.getCustomerProfileById(customerProfileId);
        if (customerProfile != null) {
            return ResponseEntity.ok(customerProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/customer-profiles/account/{accountId}")
    public ResponseEntity<List<CustomerProfile>> getCustomerProfilesByAccountId(@PathVariable Long accountId) {
        // Logic to retrieve customer profiles by account ID
        List<CustomerProfile> customerProfiles = customerProfileService.getCustomerProfilesByAccountId(accountId);
        return ResponseEntity.ok(customerProfiles);
    }

    @PutMapping("/api/customer-profiles/{customerProfileId}")
    public ResponseEntity<CustomerProfile> updateCustomerProfile(@PathVariable Long customerProfileId, @RequestBody CustomerProfile customerProfile) {
        // Logic to update a customer profile
        CustomerProfile updatedProfile = customerProfileService.updateCustomerProfile(customerProfileId, customerProfile);
        if (updatedProfile != null) {
            return ResponseEntity.ok(updatedProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/customer-profiles/{customerProfileId}")
    public ResponseEntity<Void> deleteCustomerProfile(@PathVariable Long customerProfileId) {
        // Logic to delete a customer profile
        boolean deleted = customerProfileService.deleteCustomerProfile(customerProfileId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/customer-profiles/{customerProfileId}/preferences")
    public ResponseEntity<Preference> createPreference(@PathVariable Long customerProfileId, @RequestBody Preference preference) {
        // Logic to create a new preference for the customer profile
        Preference createdPreference = preferenceService.createPreference(customerProfileId, preference);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPreference);
    }

    @DeleteMapping("/api/customer-profiles/{customerProfileId}/preferences/{tagId}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long customerProfileId, @PathVariable Long tagId) {
        // Logic to delete a preference for the customer profile
        boolean deleted = preferenceService.deletePreference(customerProfileId, tagId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/customer-profiles/{customerProfileId}/feed")
    public ResponseEntity<List<FeedItem>> getCustomerProfileFeed(@PathVariable Long customerProfileId) {
        // Logic to retrieve the feed for the customer profile
        List<FeedItem> feedItems = customerProfileService.getCustomerProfileFeed(customerProfileId);
        return ResponseEntity.ok(feedItems);
    }

    @PostMapping("/api/swap-requests")
    public ResponseEntity<SwapRequest> createSwapRequest(@RequestBody SwapRequest swapRequest) {
        // Logic to create a new swap request
        SwapRequest createdSwapRequest = swapRequestService.createSwapRequest(swapRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSwapRequest);
    }

    @GetMapping("/api/swap-requests/customer/{customerProfileId}/pending")
    public ResponseEntity<List<SwapRequest>> getPendingSwapRequests(@PathVariable Long customerProfileId) {
        // Logic to retrieve pending swap requests for the customer profile
        List<SwapRequest> pendingRequests = swapRequestService.getPendingSwapRequests(customerProfileId);
        return ResponseEntity.ok(pendingRequests);
    }

}
