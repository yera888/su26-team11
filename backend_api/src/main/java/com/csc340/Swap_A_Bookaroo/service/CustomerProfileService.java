package com.csc340.Swap_A_Bookaroo.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;

@Service
public class CustomerProfileService {

    private final SwapRequestRepository swapRequestRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final CustomerPreferenceRepository customerPreferenceRepository;
    private final BookListingRepository bookListingRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository,
                                AccountRepository accountRepository,
                                TagRepository tagRepository,
                                CustomerPreferenceRepository customerPreferenceRepository,
                                BookListingRepository bookListingRepository,
                                SwapRequestRepository swapRequestRepository) {
        this.customerProfileRepository = customerProfileRepository;
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
        this.customerPreferenceRepository = customerPreferenceRepository;
        this.bookListingRepository = bookListingRepository;
        this.swapRequestRepository = swapRequestRepository;
    }

    @Transactional
    public CustomerProfile createCustomerProfile(CustomerProfile profile) {
        Account account = profile.getAccount();
        if (account == null || accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null;
        }
        account.setRole("CUSTOMER");
        Account savedAccount = accountRepository.save(account);
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
        return customerProfileRepository.findByAccount_AccountId(accountId).orElse(null);
    }

    @Transactional
    public CustomerProfile updateCustomerProfile(Long id, CustomerProfile updatedProfile) {
        CustomerProfile existing = getCustomerById(id);
        if (existing == null) return null;
        existing.setBio(updatedProfile.getBio());
        return customerProfileRepository.save(existing);
    }

    @Transactional
    public boolean deleteCustomerProfile(Long id) {
        CustomerProfile profile = customerProfileRepository.findById(id).orElse(null);
        if (profile == null) return false;
        
        List<SwapRequest> customerSwaps = swapRequestRepository.findByCustomerProfile_CustomerProfileIdAndStatus(id, SwapRequestStatus.PENDING);
        if (customerSwaps != null && !customerSwaps.isEmpty()) {
            swapRequestRepository.deleteAll(customerSwaps);
        }
        
        Long accountId = profile.getAccount().getAccountId();
        customerProfileRepository.deleteById(id);
        accountRepository.deleteById(accountId);
        return true;
    }

    @Transactional
    public Set<Tag> addPreferenceTag(Long customerProfileId, Tag tagData) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        if (profile == null) return null;

        // Find the master tag by name. If it doesn't exist, create it once.
        Tag tag = tagRepository.findByTagName(tagData.getTagName()).orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setTagName(tagData.getTagName());
                    return tagRepository.save(newTag);
                });

        // Add the master tag directly to this user's preference set
        profile.getPreferences().add(tag);
        customerProfileRepository.save(profile);
        
        return profile.getPreferences();
    }

    public Set<Tag> getCustomerPreferences(Long customerProfileId) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        return profile != null ? profile.getPreferences() : null;
    }

    @Transactional
    public Set<Tag> removePreferenceTag(Long customerProfileId, Long tagId) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        if (profile == null) return null;

        // Remove the association from the set (this deletes the row in the join table, NOT the master tag)
        profile.getPreferences().removeIf(tag -> tag.getTagId().equals(tagId));
        customerProfileRepository.save(profile);

        return profile.getPreferences();
    }

    public List<BookListing> getMatchedBookFeed(Long customerProfileId) {
        CustomerProfile customer = getCustomerById(customerProfileId);
        if (customer == null) return Collections.emptyList();

        List<BookListing> allAvailable = bookListingRepository.findByStatus(ListingStatus.AVAILABLE);
        Set<Tag> prefs = customer.getPreferences();

        if (prefs == null || prefs.isEmpty()) return allAvailable;

        List<String> preferredNames = prefs.stream().filter(tag -> tag != null && tag.getTagName() != null)
                                                    .map(tag -> tag.getTagName().toLowerCase())
                                                    .filter(name -> !name.isEmpty()).toList();

        return allAvailable.stream().filter(b -> b.getTags() != null && b.getTags().stream().anyMatch(tag -> tag != null 
                                     && tag.getTagName() != null 
                                     && preferredNames.contains(tag.getTagName().toLowerCase())))
                .toList();
    }
}