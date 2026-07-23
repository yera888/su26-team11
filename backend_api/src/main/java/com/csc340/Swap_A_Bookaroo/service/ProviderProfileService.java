package com.csc340.Swap_A_Bookaroo.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;

@Service
public class ProviderProfileService {

    private final ProviderProfileRepository providerProfileRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final BookListingRepository bookListingRepository;
    private final SwapRequestRepository swapRequestRepository;

    public ProviderProfileService(
            ProviderProfileRepository providerProfileRepository,
            AccountRepository accountRepository,
            AccountService accountService,
            BookListingRepository bookListingRepository,
            SwapRequestRepository swapRequestRepository) {
        this.providerProfileRepository = providerProfileRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
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

    public ProviderProfile getProviderProfileByUsername(String username) {
        return providerProfileRepository.findByAccount_Username(username).orElse(null);
    }

    @Transactional
    public ProviderProfile createProviderProfile(ProviderProfile providerProfile) {
        if (providerProfile == null || providerProfile.getAccount() == null) {
            return null;
        }

        String username = providerProfile.getAccount().getUsername();
        Account existingAccount = accountRepository.findByUsername(username).orElse(null);

        if (existingAccount != null) {
            // Account already exists (e.g. as Customer). Attach profile & append role
            providerProfile.setAccount(existingAccount);
            providerProfile.setSwapCreditBalance(0);
            accountService.addRoleToAccount(existingAccount, "PROVIDER");
            return providerProfileRepository.save(providerProfile);
        }

        // New account flow
        Account savedAccount = accountService.registerAccount(providerProfile.getAccount(), "PROVIDER");
        if (savedAccount == null) {
            return null;
        }

        providerProfile.setAccount(savedAccount);
        providerProfile.setSwapCreditBalance(0);
        return providerProfileRepository.save(providerProfile);
    }

    @Transactional
    public boolean deleteProviderProfile(Long providerProfileId) {
        ProviderProfile providerProfile = providerProfileRepository.findById(providerProfileId).orElse(null);
        if (providerProfile == null) {
            return false;
        }

        Account account = providerProfile.getAccount();

        // Delete swap requests for this provider's book listings
        List<SwapRequest> providerRequests =
                swapRequestRepository.findByBookListing_ProviderProfile_ProviderProfileId(providerProfileId);
        if (!providerRequests.isEmpty()) {
            swapRequestRepository.deleteAll(providerRequests);
        }

        // If user is also a Customer, delete their customer swap requests
        if (account.getCustomerProfile() != null) {
            Long customerId = account.getCustomerProfile().getCustomerProfileId();
            List<SwapRequest> customerRequests =
                    swapRequestRepository.findByCustomerProfile_CustomerProfileId(customerId);
            if (!customerRequests.isEmpty()) {
                swapRequestRepository.deleteAll(customerRequests);
            }
        }

        // Delete the entire Account (Cascades to ProviderProfile, CustomerProfile, BookListings, and Preferences)
        accountRepository.delete(account);

        return true;
    }

    @Transactional
    public boolean deleteProviderProfileForUsername(String username) {
        ProviderProfile providerProfile = getProviderProfileByUsername(username);
        if (providerProfile != null) {
            return deleteProviderProfile(providerProfile.getProviderProfileId());
        }

        // Fallback: If ProviderProfile was missing but Account exists
        Account account = accountRepository.findByUsername(username).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            return true;
        }

        return false;
    }

    public List<BookListing> getActiveListings(Long providerProfileId) {
        return bookListingRepository.findByProviderProfile_ProviderProfileIdAndStatusIn(
                providerProfileId,
                Arrays.asList(ListingStatus.AVAILABLE, ListingStatus.REQUESTED));
    }

    public List<BookListing> getActiveListingsForUsername(String username) {
        return bookListingRepository
                .findByProviderProfile_Account_UsernameAndStatusInOrderByDatePostedDesc(
                        username,
                        Arrays.asList(ListingStatus.AVAILABLE, ListingStatus.REQUESTED));
    }

    public List<SwapRequest> getPendingSwapRequests(Long providerProfileId) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_ProviderProfileIdAndStatus(
                        providerProfileId,
                        SwapRequestStatus.PENDING);
    }

    public List<SwapRequest> getPendingSwapRequestsForUsername(String username) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_Account_UsernameAndStatusOrderByRequestDateDesc(
                        username,
                        SwapRequestStatus.PENDING);
    }

    public List<SwapRequest> getApprovedSwapRequestsForUsername(String username) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_Account_UsernameAndStatusOrderByRequestDateDesc(
                        username,
                        SwapRequestStatus.APPROVED);
    }

    public List<SwapRequest> getSwapHistory(Long providerProfileId) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_ProviderProfileIdAndStatusIn(
                        providerProfileId,
                        Arrays.asList(
                                SwapRequestStatus.COMPLETED,
                                SwapRequestStatus.REJECTED,
                                SwapRequestStatus.CANCELLED));
    }

    public List<SwapRequest> getSwapHistoryForUsername(String username) {
        return swapRequestRepository
                .findByBookListing_ProviderProfile_Account_UsernameAndStatusInOrderByRequestDateDesc(
                        username,
                        Arrays.asList(
                                SwapRequestStatus.COMPLETED,
                                SwapRequestStatus.REJECTED,
                                SwapRequestStatus.CANCELLED));
    }

    public long getCompletedSwapCountForUsername(String username) {
        return swapRequestRepository
                .countByBookListing_ProviderProfile_Account_UsernameAndStatus(
                        username,
                        SwapRequestStatus.COMPLETED);
    }
}