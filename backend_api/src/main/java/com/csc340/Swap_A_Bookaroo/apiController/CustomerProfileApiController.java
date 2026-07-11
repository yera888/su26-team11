package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@RestController
@RequestMapping("/api/customer-profiles")
public class CustomerProfileApiController {

    private final CustomerProfileService customerProfileService;
    private final SwapRequestService swapRequestService;

    public CustomerProfileApiController(CustomerProfileService customerProfileService, SwapRequestService swapRequestService) {
        this.customerProfileService = customerProfileService;
        this.swapRequestService = swapRequestService;
    }

    @PostMapping
    public ResponseEntity<CustomerProfile> createCustomerProfile(@RequestBody CustomerProfile profile) {
        CustomerProfile created = customerProfileService.createCustomerProfile(profile);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
        return ResponseEntity.ok(customerProfileService.getAllCustomers());
    }

    @GetMapping("/{customerProfileId}")
    public ResponseEntity<CustomerProfile> getCustomerById(@PathVariable Long customerProfileId) {
        CustomerProfile profile = customerProfileService.getCustomerById(customerProfileId);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<CustomerProfile> getCustomerByAccountId(@PathVariable Long accountId) {
        CustomerProfile profile = customerProfileService.getCustomerByAccountId(accountId);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{customerProfileId}")
    public ResponseEntity<CustomerProfile> updateCustomerProfile(@PathVariable Long customerProfileId, @RequestBody CustomerProfile updatedProfile) {
        CustomerProfile profile = customerProfileService.updateCustomerProfile(customerProfileId, updatedProfile);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{customerProfileId}")
    public ResponseEntity<Void> deleteCustomerProfile(@PathVariable Long customerProfileId) {
        return customerProfileService.deleteCustomerProfile(customerProfileId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{customerProfileId}/preferences")
    public ResponseEntity<List<CustomerPreference>> addPreferenceTag(@PathVariable Long customerProfileId, @RequestBody Tag tagData) {
        List<CustomerPreference> preferences = customerProfileService.addPreferenceTag(customerProfileId, tagData);
        return preferences != null ? ResponseEntity.ok(preferences) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{customerProfileId}/preferences")
    public ResponseEntity<List<CustomerPreference>> getCustomerPreferences(@PathVariable Long customerProfileId) {
        List<CustomerPreference> preferences = customerProfileService.getCustomerPreferences(customerProfileId);
        return ResponseEntity.ok(preferences != null ? preferences : Collections.emptyList());
    }

    @DeleteMapping("/{customerProfileId}/preferences/{tagId}")
    public ResponseEntity<List<CustomerPreference>> removePreferenceTag(@PathVariable Long customerProfileId, @PathVariable Long tagId) {
        List<CustomerPreference> preferences = customerProfileService.removePreferenceTag(customerProfileId, tagId);
        return preferences != null ? ResponseEntity.ok(preferences) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{customerProfileId}/feed")
    public ResponseEntity<List<BookListing>> getMatchedBookFeed(@PathVariable Long customerProfileId) {
        return ResponseEntity.ok(customerProfileService.getMatchedBookFeed(customerProfileId));
    }
}