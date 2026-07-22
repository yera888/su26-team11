package com.csc340.Swap_A_Bookaroo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.repository.AccountRepository;
import com.csc340.Swap_A_Bookaroo.repository.ProviderProfileRepository;

@Service
public class ProviderProfileService {

    private final ProviderProfileRepository providerProfileRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public ProviderProfileService(
            ProviderProfileRepository providerProfileRepository,
            AccountRepository accountRepository,
            AccountService accountService) {
        this.providerProfileRepository = providerProfileRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public List<ProviderProfile> getAllProviderProfiles() {
        return providerProfileRepository.findAll();
    }

    public ProviderProfile getProviderProfileById(Long providerProfileId) {
        return providerProfileRepository.findById(providerProfileId).orElse(null);
    }

    public ProviderProfile getProviderProfileByAccountId(Long accountId) {
        return providerProfileRepository.findByAccount_AccountId(accountId).orElse(null);
    }

    public ProviderProfile getProviderProfileByUsername(String username) {
        return providerProfileRepository.findByAccount_Username(username).orElse(null);
    }

    @Transactional
    public ProviderProfile createProviderProfile(ProviderProfile providerProfile) {
        if (providerProfile == null || providerProfile.getAccount() == null) {
            return null;
        }

        Account savedAccount = accountService.registerAccount(
                providerProfile.getAccount(),
                "PROVIDER");

        if (savedAccount == null) {
            return null;
        }

        providerProfile.setAccount(savedAccount);
        providerProfile.setSwapCreditBalance(0);
        return providerProfileRepository.save(providerProfile);
    }

    @Transactional
    public boolean deleteProviderProfile(Long providerProfileId) {
        ProviderProfile providerProfile = providerProfileRepository
                .findById(providerProfileId)
                .orElse(null);

        if (providerProfile == null) {
            return false;
        }

        Long accountId = providerProfile.getAccount().getAccountId();
        providerProfileRepository.delete(providerProfile);
        accountRepository.deleteById(accountId);
        return true;
    }
}



