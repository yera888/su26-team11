package com.csc340.Swap_A_Bookaroo.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;

@Service
public class ProviderProfileService {

    private final ProviderProfileRepository providerProfileRepository;
    private final AccountRepository accountRepository;
    private final BookListingRepository bookListingRepository;
    private final SwapRequestRepository swapRequestRepository;

    public ProviderProfileService(ProviderProfileRepository providerProfileRepository,
                                  AccountRepository accountRepository,
                                  BookListingRepository bookListingRepository,
                                  SwapRequestRepository swapRequestRepository) {
        this.providerProfileRepository = providerProfileRepository;
        this.accountRepository = accountRepository;
        this.bookListingRepository = bookListingRepository;
        this.swapRequestRepository = swapRequestRepository;
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

    public ProviderProfile createProviderProfile(ProviderProfile providerProfile) {
        Account account = providerProfile.getAccount();
        if (account == null || accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null;
        }
        account.setRole("PROVIDER");
        Account savedAccount = accountRepository.save(account);
        providerProfile.setAccount(savedAccount);
        return providerProfileRepository.save(providerProfile);
    }

    public ProviderProfile updateProviderProfile(Long providerProfileId, ProviderProfile updatedProviderProfile) {
        ProviderProfile existingProfile = providerProfileRepository.findById(providerProfileId).orElse(null);
        if (existingProfile != null) {
            existingProfile.setBio(updatedProviderProfile.getBio());
            return providerProfileRepository.save(existingProfile);
        }
        return null;
    }

    public boolean deleteProviderProfile(Long providerProfileId) {
        ProviderProfile providerProfile = providerProfileRepository.findById(providerProfileId).orElse(null);
        if (providerProfile == null) return false;
        Long accountId = providerProfile.getAccount().getAccountId();
        providerProfileRepository.deleteById(providerProfileId);
        accountRepository.deleteById(accountId);
        return true;
    }

    public List<BookListing> getActiveListings(Long providerProfileId) {
        return bookListingRepository.findByProviderProfile_ProviderProfileIdAndStatusIn(providerProfileId,
                Arrays.asList(ListingStatus.AVAILABLE, ListingStatus.REQUESTED));
    }

    public List<SwapRequest> getPendingSwapRequests(Long providerProfileId) {
        return swapRequestRepository.findByBookListing_ProviderProfile_ProviderProfileIdAndStatus(providerProfileId,
                SwapRequestStatus.PENDING);
    }

    public List<SwapRequest> getSwapHistory(Long providerProfileId) {
        return swapRequestRepository.findByBookListing_ProviderProfile_ProviderProfileIdAndStatusIn(providerProfileId,
                Arrays.asList(SwapRequestStatus.COMPLETED, SwapRequestStatus.REJECTED, SwapRequestStatus.CANCELLED));
    }
}