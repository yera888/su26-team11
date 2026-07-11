package com.csc340.Swap_A_Bookaroo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.repository.ProviderProfileRepository;

@Service
@Transactional
public class ProviderProfileService {

    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @Autowired
    private com.csc340.Swap_A_Bookaroo.repository.AccountRepository accountRepository;

    public ProviderProfile upgradeCustomerToProvider(Long accountId) {
        // 1. Find the existing account (which belongs to a Customer)
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        // 2. Check if they are already a provider to avoid duplicate profiles
        Optional<ProviderProfile> existingProvider = providerProfileRepository.findByAccountId(accountId);
        if (existingProvider.isPresent()) {
            throw new RuntimeException("This account is already registered as a provider!");
        }

        // 3. Upgrade their security/access role to DUAL
        existingAccount.setRole("DUAL");
        accountRepository.save(existingAccount); // Update the account table

        // 4. Create and link their new Provider Profile
        ProviderProfile newProviderProfile = new ProviderProfile();
        newProviderProfile.setAccount(existingAccount);
        newProviderProfile.setBookListings(new ArrayList<>()); // Initialize their store shelf

        // 5. Save and return the new profile
        return providerProfileRepository.save(newProviderProfile);
    }

    public ProviderProfile createProviderProfile(ProviderProfile profile) {
        if (profile.getAccount() != null) {
            profile.getAccount().setRole("PROVIDER"); // Force role assignment
        }
        profile.setBookListings(new ArrayList<>()); // Initialize an empty book list
        return providerProfileRepository.save(profile);
    }

    public List<ProviderProfile> getAllProviders() {
        return providerProfileRepository.findAll();
    }

    public ProviderProfile getProviderById(Long id) {
        return providerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider Profile not found with id: " + id));
    }

    public ProviderProfile getProviderByAccountId(Long accountId) {
        return providerProfileRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Provider Profile not found for account id: " + accountId));
    }

    public ProviderProfile updateProviderProfile(Long id, ProviderProfile updatedProfile) {
        ProviderProfile existing = getProviderById(id);
        if (updatedProfile.getAccount() != null && existing.getAccount() != null) {
            Account existingAcc = existing.getAccount();
            Account updatedAcc = updatedProfile.getAccount();
            existingAcc.setFirstName(updatedAcc.getFirstName());
            existingAcc.setLastName(updatedAcc.getLastName());
            existingAcc.setUsername(updatedAcc.getUsername());
            existingAcc.setPassword(updatedAcc.getPassword());
        }
        return providerProfileRepository.save(existing);
    }

    public void deleteProviderProfile(Long id) {
        providerProfileRepository.deleteById(id);
    }
}