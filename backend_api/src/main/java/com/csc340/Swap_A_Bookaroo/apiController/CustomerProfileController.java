package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.entities.Tag;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;

@RestController
@RequestMapping("/api/customer-profiles")
public class CustomerProfileController {

    @Autowired
    private CustomerProfileService customerProfileService;

    @PostMapping
    public ResponseEntity<CustomerProfile> createCustomer(@RequestBody CustomerProfile profile) {
        return ResponseEntity.ok(customerProfileService.createCustomerProfile(profile));
    }

    @GetMapping
    public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
        return ResponseEntity.ok(customerProfileService.getAllCustomers());
    }

    @GetMapping("/{customerProfileId}")
    public ResponseEntity<CustomerProfile> getCustomerById(@PathVariable Long customerProfileId) {
        return ResponseEntity.ok(customerProfileService.getCustomerById(customerProfileId));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<CustomerProfile> getCustomerByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(customerProfileService.getCustomerByAccountId(accountId));
    }

    @PutMapping("/{customerProfileId}")
    public ResponseEntity<CustomerProfile> updateCustomerProfile(
            @PathVariable Long customerProfileId, 
            @RequestBody CustomerProfile profile) {
        return ResponseEntity.ok(customerProfileService.updateCustomerProfile(customerProfileId, profile));
    }

    @DeleteMapping("/{customerProfileId}")
    public ResponseEntity<Void> deleteCustomerProfile(@PathVariable Long customerProfileId) {
        customerProfileService.deleteCustomerProfile(customerProfileId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerProfileId}/preferences")
    public ResponseEntity<List<Tag>> addCustomerPreference(
            @PathVariable Long customerProfileId, 
            @RequestBody Tag tag) {
        return ResponseEntity.ok(customerProfileService.addPreferenceTag(customerProfileId, tag));
    }

    @GetMapping("/{customerProfileId}/preferences")
    public ResponseEntity<List<Tag>> getCustomerPreferences(@PathVariable Long customerProfileId) {
        return ResponseEntity.ok(customerProfileService.getCustomerPreferences(customerProfileId));
    }

    @DeleteMapping("/{customerProfileId}/preferences/{tagId}")
    public ResponseEntity<List<Tag>> removeCustomerPreference(
            @PathVariable Long customerProfileId, 
            @PathVariable Long tagId) {
        return ResponseEntity.ok(customerProfileService.removePreferenceTag(customerProfileId, tagId));
    }

    @GetMapping("/{customerProfileId}/feed")
    public ResponseEntity<List<Object>> getMatchedBookFeed(@PathVariable Long customerProfileId) {
        // Feed matching filtering logic would be placed here
        return ResponseEntity.ok(new ArrayList<>());
    }
}
