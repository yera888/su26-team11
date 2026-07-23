package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.BookListing;
import com.csc340.Swap_A_Bookaroo.entities.CustomerPreference;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.entities.Tag;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;

@RestController
@RequestMapping("/api/customer-profiles")
public class CustomerProfileApiController {

    private final CustomerProfileService customerProfileService;

    public CustomerProfileApiController(
            CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    // Public in SecurityConfig so a new customer can register.
    @PostMapping
    public ResponseEntity<CustomerProfile> createCustomerProfile(
            @RequestBody CustomerProfile profile) {
        CustomerProfile created =
                customerProfileService.createCustomerProfile(profile);

        return created != null
                ? ResponseEntity.ok(created)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerProfile> getCurrentCustomer(
            Authentication authentication) {
        CustomerProfile profile =
                customerProfileService.getCustomerByUsername(
                        authentication.getName());

        return profile != null
                ? ResponseEntity.ok(profile)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/me")
    public ResponseEntity<CustomerProfile> updateCurrentCustomer(
            Authentication authentication,
            @RequestBody CustomerProfile updatedProfile) {
        CustomerProfile profile =
                customerProfileService.updateCustomerProfileForUsername(
                        authentication.getName(),
                        updatedProfile);

        return profile != null
                ? ResponseEntity.ok(profile)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentCustomer(
            Authentication authentication) {
        return customerProfileService.deleteCustomerProfileForUsername(
                authentication.getName())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/me/preferences")
    public ResponseEntity<List<CustomerPreference>> addPreferenceTag(
            Authentication authentication,
            @RequestBody Tag tagData) {
        List<CustomerPreference> preferences =
                customerProfileService.addPreferenceTagForUsername(
                        authentication.getName(),
                        tagData);

        return preferences != null
                ? ResponseEntity.ok(preferences)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/me/preferences")
    public ResponseEntity<List<CustomerPreference>> getPreferences(
            Authentication authentication) {
        List<CustomerPreference> preferences =
                customerProfileService.getCustomerPreferencesForUsername(
                        authentication.getName());

        return ResponseEntity.ok(
                preferences == null
                        ? Collections.emptyList()
                        : preferences);
    }

        @DeleteMapping("/me/preferences/{tagName}")
        public ResponseEntity<List<CustomerPreference>> removePreferenceTag(
                @PathVariable String tagName,
                Authentication authentication) {
        List<CustomerPreference> preferences =
                customerProfileService.removePreferenceTagForUsername(
                        authentication.getName(),
                        tagName);

        return preferences != null
                ? ResponseEntity.ok(preferences)
                : ResponseEntity.notFound().build();
        }

    @GetMapping("/me/feed")
    public ResponseEntity<List<BookListing>> getMatchedBookFeed(
            Authentication authentication) {
        return ResponseEntity.ok(
                customerProfileService.getMatchedBookFeedForUsername(
                        authentication.getName()));
    }
}