package com.csc340.Swap_A_Bookaroo.apiController;

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

import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;

@RestController
@RequestMapping("/api/provider-profiles")
public class ProviderProfileController {

    @Autowired
    private ProviderProfileService providerProfileService;

    @PostMapping
    public ResponseEntity<ProviderProfile> createProvider(@RequestBody ProviderProfile profile) {
        return ResponseEntity.ok(providerProfileService.createProviderProfile(profile));
    }
    @PostMapping("/account/{accountId}/upgrade-to-provider")
    public ResponseEntity<ProviderProfile> upgradeAccountToProvider(@PathVariable Long accountId) {
        return ResponseEntity.ok(providerProfileService.upgradeCustomerToProvider(accountId));
    }

    @GetMapping
    public ResponseEntity<List<ProviderProfile>> getAllProviders() {
        return ResponseEntity.ok(providerProfileService.getAllProviders());
    }

    @GetMapping("/{providerProfileId}")
    public ResponseEntity<ProviderProfile> getProviderById(@PathVariable Long providerProfileId) {
        return ResponseEntity.ok(providerProfileService.getProviderById(providerProfileId));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ProviderProfile> getProviderByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(providerProfileService.getProviderByAccountId(accountId));
    }

    @PutMapping("/{providerProfileId}")
    public ResponseEntity<ProviderProfile> updateProviderProfile(
            @PathVariable Long providerProfileId, 
            @RequestBody ProviderProfile profile) {
        return ResponseEntity.ok(providerProfileService.updateProviderProfile(providerProfileId, profile));
    }

    @DeleteMapping("/{providerProfileId}")
    public ResponseEntity<Void> deleteProviderProfile(@PathVariable Long providerProfileId) {
        providerProfileService.deleteProviderProfile(providerProfileId);
        return ResponseEntity.noContent().build();
    }
}