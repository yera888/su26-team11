package com.csc340.Swap_A_Bookaroo.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;

@Service
public class CustomerProfileService {

    private final SwapRequestRepository swapRequestRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TagRepository tagRepository;
    private final CustomerPreferenceRepository customerPreferenceRepository;
    private final BookListingRepository bookListingRepository;

    public CustomerProfileService(
            CustomerProfileRepository customerProfileRepository,
            AccountRepository accountRepository,
            AccountService accountService,
            TagRepository tagRepository,
            CustomerPreferenceRepository customerPreferenceRepository,
            BookListingRepository bookListingRepository,
            SwapRequestRepository swapRequestRepository) {
        this.customerProfileRepository = customerProfileRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.tagRepository = tagRepository;
        this.customerPreferenceRepository = customerPreferenceRepository;
        this.bookListingRepository = bookListingRepository;
        this.swapRequestRepository = swapRequestRepository;
    }

    @Transactional
    public CustomerProfile createCustomerProfile(CustomerProfile profile) {
        if (profile == null || profile.getAccount() == null) {
            return null;
        }

        String username = profile.getAccount().getUsername();
        Account existingAccount = accountRepository.findByUsername(username).orElse(null);

        if (existingAccount != null) {
            // Account already exists (e.g. as Provider). Attach profile & append role
            profile.setAccount(existingAccount);
            accountService.addRoleToAccount(existingAccount, "CUSTOMER");
            return customerProfileRepository.save(profile);
        }

        // New account flow
        Account savedAccount = accountService.registerAccount(profile.getAccount(), "CUSTOMER");
        if (savedAccount == null) {
            return null;
        }

        profile.setAccount(savedAccount);
        return customerProfileRepository.save(profile);
    }

    public List<CustomerProfile> getAllCustomers() {
        return customerProfileRepository.findAll();
    }

    public CustomerProfile getCustomerById(Long id) {
        return customerProfileRepository.findById(id).orElse(null);
    }

    public CustomerProfile getCustomerByAccountId(Long accountId) {
        return customerProfileRepository
                .findByAccount_AccountId(accountId)
                .orElse(null);
    }

    public CustomerProfile getCustomerByUsername(String username) {
        return customerProfileRepository
                .findByAccount_Username(username)
                .orElse(null);
    }

    @Transactional
    public CustomerProfile updateCustomerProfile(Long id, CustomerProfile updatedProfile) {
        CustomerProfile existing = getCustomerById(id);
        return updateExistingProfile(existing, updatedProfile);
    }

    @Transactional
    public CustomerProfile updateCustomerProfileForUsername(String username, CustomerProfile updatedProfile) {
        CustomerProfile existing = getCustomerByUsername(username);
        return updateExistingProfile(existing, updatedProfile);
    }

    @Transactional
    public boolean deleteCustomerProfile(Long id) {
        CustomerProfile profile = getCustomerById(id);
        if (profile == null) {
            return false;
        }

        Account account = profile.getAccount();

        // Delete swap requests where this account is the Customer
        List<SwapRequest> customerRequests =
                swapRequestRepository.findByCustomerProfile_CustomerProfileId(id);
        if (!customerRequests.isEmpty()) {
            swapRequestRepository.deleteAll(customerRequests);
        }

        // If user is also a Provider, delete swap requests for their book listings
        if (account.getProviderProfile() != null) {
            Long providerId = account.getProviderProfile().getProviderProfileId();
            List<SwapRequest> providerRequests =
                    swapRequestRepository.findByBookListing_ProviderProfile_ProviderProfileId(providerId);
            if (!providerRequests.isEmpty()) {
                swapRequestRepository.deleteAll(providerRequests);
            }
        }

        // Delete the entire Account (Cascades to CustomerProfile, ProviderProfile, BookListings, and Preferences)
        accountRepository.delete(account);

        return true;
    }

    @Transactional
    public boolean deleteCustomerProfileForUsername(String username) {
        CustomerProfile profile = getCustomerByUsername(username);
        if (profile != null) {
            return deleteCustomerProfile(profile.getCustomerProfileId());
        }

        // Fallback: If for some reason CustomerProfile was missing but Account exists
        Account account = accountRepository.findByUsername(username).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            return true;
        }

        return false;
    }

    @Transactional
    public List<CustomerPreference> addPreferenceTag(Long customerProfileId, Tag tagData) {
        return addPreferenceToProfile(getCustomerById(customerProfileId), tagData);
    }

    @Transactional
    public List<CustomerPreference> addPreferenceTagForUsername(String username, Tag tagData) {
        return addPreferenceToProfile(getCustomerByUsername(username), tagData);
    }

    public List<CustomerPreference> getCustomerPreferences(Long customerProfileId) {
        return preferencesOf(getCustomerById(customerProfileId));
    }

    public List<CustomerPreference> getCustomerPreferencesForUsername(String username) {
        return customerPreferenceRepository.findByCustomerProfile_Account_Username(username);
    }

    @Transactional
    public List<CustomerPreference> removePreferenceTag(Long customerProfileId, Long preferenceId) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        if (profile == null) {
            return null;
        }

        CustomerPreference preference = customerPreferenceRepository
                .findById(preferenceId)
                .filter(item -> item.getCustomerProfile() != null
                        && customerProfileId.equals(item.getCustomerProfile().getCustomerProfileId()))
                .orElse(null);

        if (preference == null) {
            return null;
        }

        customerPreferenceRepository.delete(preference);
        return preferencesOf(getCustomerById(customerProfileId));
    }

    @Transactional
    public List<CustomerPreference> removePreferenceTagForUsername(String username, String tagName) {
        if (tagName == null || tagName.isBlank()) {
            return getCustomerPreferencesForUsername(username);
        }

        CustomerPreference preference = customerPreferenceRepository
                .findByCustomerProfile_Account_UsernameAndTag_TagNameIgnoreCase(
                        username,
                        tagName.trim())
                .orElse(null);

        if (preference != null) {
            CustomerProfile profile = preference.getCustomerProfile();
            if (profile != null && profile.getPreferences() != null) {
                profile.getPreferences().remove(preference);
            }

            customerPreferenceRepository.delete(preference);
            customerPreferenceRepository.flush();
        }

        return getCustomerPreferencesForUsername(username);
    }

    public List<BookListing> getMatchedBookFeed(Long customerProfileId) {
        return matchedFeedFor(getCustomerById(customerProfileId));
    }

    public List<BookListing> getMatchedBookFeedForUsername(String username) {
        return matchedFeedFor(getCustomerByUsername(username));
    }

    private CustomerProfile updateExistingProfile(CustomerProfile existing, CustomerProfile updatedProfile) {
        if (existing == null || updatedProfile == null) {
            return null;
        }

        existing.setBio(updatedProfile.getBio());
        return customerProfileRepository.save(existing);
    }

    private List<CustomerPreference> addPreferenceToProfile(CustomerProfile profile, Tag tagData) {
        if (profile == null || tagData == null || tagData.getTagName() == null || tagData.getTagName().isBlank()) {
            return null;
        }

        String normalizedName = tagData.getTagName().trim();

        Tag persistedTag = tagRepository.findByTagName(normalizedName)
                .orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setTagName(normalizedName);
                    return tagRepository.save(newTag);
                });

        boolean alreadyPresent = customerPreferenceRepository
                .existsByCustomerProfile_CustomerProfileIdAndTag_TagNameIgnoreCase(
                        profile.getCustomerProfileId(),
                        normalizedName);

        if (!alreadyPresent) {
            CustomerPreference preference = new CustomerPreference();
            preference.setTag(persistedTag);
            preference.setCustomerProfile(profile);
            customerPreferenceRepository.save(preference);
        }

        return preferencesOf(getCustomerById(profile.getCustomerProfileId()));
    }

    private List<CustomerPreference> preferencesOf(CustomerProfile profile) {
        if (profile == null) {
            return null;
        }
        return profile.getPreferences() == null
                ? Collections.emptyList()
                : profile.getPreferences();
    }

    private List<BookListing> matchedFeedFor(CustomerProfile customer) {
        if (customer == null) {
            return Collections.emptyList();
        }

        List<BookListing> allAvailable = bookListingRepository.findByStatus(ListingStatus.AVAILABLE);
        List<CustomerPreference> preferences = preferencesOf(customer);

        if (preferences == null || preferences.isEmpty()) {
            return allAvailable;
        }

        List<String> preferredNames = preferences.stream()
                .map(CustomerPreference::getTagName)
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .toList();

        return allAvailable.stream()
                .filter(book -> book.getListingTags() != null
                        && book.getListingTags().stream()
                                .anyMatch(listingTag -> listingTag.getTag() != null
                                        && listingTag.getTag().getTagName() != null
                                        && preferredNames.contains(
                                                listingTag.getTag().getTagName().toLowerCase())))
                .toList();
    }
}