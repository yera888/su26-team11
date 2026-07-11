package com.csc340.Swap_A_Bookaroo.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.repository.*;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;
    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final CustomerPreferenceRepository customerPreferenceRepository;
    private final BookListingRepository bookListingRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository,
                                  AccountRepository accountRepository,
                                  TagRepository tagRepository,
                                  CustomerPreferenceRepository customerPreferenceRepository,
                                  BookListingRepository bookListingRepository) {
        this.customerProfileRepository = customerProfileRepository;
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
        this.customerPreferenceRepository = customerPreferenceRepository;
        this.bookListingRepository = bookListingRepository;
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
        
        Long accountId = profile.getAccount().getAccountId();
        customerProfileRepository.deleteById(id);
        accountRepository.deleteById(accountId);
        return true;
    }

    @Transactional
    public List<CustomerPreference> addPreferenceTag(Long customerProfileId, Tag tagData) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        if (profile == null) return null;

        Tag tag = tagRepository.findByTagName(tagData.getTagName())
                .orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.createTag(tagData.getTagName());
                    return tagRepository.save(newTag);
                });

        boolean matches = profile.getPreferences().stream()
                .anyMatch(pref -> pref.getTagName().equalsIgnoreCase(tag.getTagName()));

        if (!matches) {
            CustomerPreference preference = new CustomerPreference();
            preference.setTagName(tag.getTagName());
            preference.setCustomerProfile(profile);
            customerPreferenceRepository.save(preference);
        }
        return getCustomerById(customerProfileId).getPreferences();
    }

    public List<CustomerPreference> getCustomerPreferences(Long customerProfileId) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        return profile != null ? profile.getPreferences() : null;
    }

    @Transactional
    public List<CustomerPreference> removePreferenceTag(Long customerProfileId, Long tagId) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        if (profile == null) return null;
        customerPreferenceRepository.deleteById(tagId);
        return getCustomerById(customerProfileId).getPreferences();
    }

    public List<BookListing> getMatchedBookFeed(Long customerProfileId) {
        CustomerProfile customer = getCustomerById(customerProfileId);
        if (customer == null) return Collections.emptyList();

        List<BookListing> allAvailable = bookListingRepository.findByStatus(ListingStatus.AVAILABLE);
        List<CustomerPreference> prefs = customer.getPreferences();

        if (prefs == null || prefs.isEmpty()) return allAvailable;

        List<String> preferredNames = prefs.stream().map(Tag::getTagName).map(String::toLowerCase).toList();

        return allAvailable.stream()
                .filter(b -> b.getListingTags().stream()
                        .anyMatch(lt -> lt.getTag() != null && preferredNames.contains(lt.getTag().getTagName().toLowerCase())))
                .toList();
    }
}