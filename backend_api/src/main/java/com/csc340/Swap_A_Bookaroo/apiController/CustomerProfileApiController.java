package com.csc340.Swap_A_Bookaroo.apiController;

import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.*;
import com.csc340.Swap_A_Bookaroo.service.CustomerProfileService;
import com.csc340.Swap_A_Bookaroo.service.SwapRequestService;

@RestController
@RequestMapping("/api/customer-profiles")
public class CustomerProfileApiController {
/* 
    private final CustomerProfileService customerProfileService;
    private final SwapRequestService swapRequestService;

    public CustomerProfileApiController(CustomerProfileService customerProfileService, SwapRequestService swapRequestService) {
        this.customerProfileService = customerProfileService;
        this.swapRequestService = swapRequestService;
    }

    @PostMapping
    public ResponseEntity<CustomerProfile> createCustomerProfile(@RequestBody CustomerProfile profile) {
        CustomerProfile created = customerProfileService.createCustomerProfile(profile);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerProfile>> getAllCustomers() {
        return ResponseEntity.ok(customerProfileService.getAllCustomers());
    }

    @GetMapping("/{customerProfileId}")
    public ResponseEntity<CustomerProfile> getCustomerById(@PathVariable Long customerProfileId) {
        CustomerProfile profile = customerProfileService.getCustomerById(customerProfileId);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<CustomerProfile> getCustomerByAccountId(@PathVariable Long accountId) {
        CustomerProfile profile = customerProfileService.getCustomerByAccountId(accountId);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{customerProfileId}")
    public ResponseEntity<CustomerProfile> updateCustomerProfile(@PathVariable Long customerProfileId, @RequestBody CustomerProfile updatedProfile) {
        CustomerProfile profile = customerProfileService.updateCustomerProfile(customerProfileId, updatedProfile);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{customerProfileId}")
    public ResponseEntity<Void> deleteCustomerProfile(@PathVariable Long customerProfileId) {
        return customerProfileService.deleteCustomerProfile(customerProfileId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /*@PostMapping("/{customerProfileId}/preferences")
    public ResponseEntity<List<CustomerPreference>> addPreferenceTag(@PathVariable Long customerProfileId, @RequestBody Tag tagData) {
        List<CustomerPreference> preferences = customerProfileService.addPreferenceTag(customerProfileId, tagData);
        return preferences != null ? ResponseEntity.ok(preferences) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{customerProfileId}/preferences")
    public ResponseEntity<List<CustomerPreference>> getCustomerPreferences(@PathVariable Long customerProfileId) {
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
        
        // FIXED: Changed type from List<CustomerPreference> to Set<Tag>
        Set<Tag> prefs = customer.getPreferences();

        if (prefs == null || prefs.isEmpty()) return allAvailable;

        List<String> preferredNames = prefs.stream().map(Tag::getTagName).map(String::toLowerCase).toList();

        return allAvailable.stream()
                .filter(b -> b.getListingTags().stream()
                        .anyMatch(lt -> lt.getTag() != null && preferredNames.contains(lt.getTag().getTagName().toLowerCase())))
                .toList();
    }
} preferences = customerProfileService.getCustomerPreferences(customerProfileId);
        return ResponseEntity.ok(preferences != null ? preferences : Collections.emptyList());
    }

    /*@DeleteMapping("/{customerProfileId}/preferences/{tagId}")
    public ResponseEntity<List<CustomerPreference>> removePreferenceTag(@PathVariable Long customerProfileId, @PathVariable Long tagId) {
        List<CustomerPreference> preferences = customerProfileService.removePreferenceTag(customerProfileId, tagId);
        return preferences != null ? ResponseEntity.ok(preferences) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{customerProfileId}/feed")
    public ResponseEntity<List<BookListing>> getMatchedBookFeed(@PathVariable Long customerProfileId) {
        return ResponseEntity.ok(customerProfileService.getMatchedBookFeed(customerProfileId));
    }
*/ }