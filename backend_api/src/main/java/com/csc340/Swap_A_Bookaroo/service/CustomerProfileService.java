package com.csc340.Swap_A_Bookaroo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.entities.Tag;
import com.csc340.Swap_A_Bookaroo.repository.AccountRepository;
import com.csc340.Swap_A_Bookaroo.repository.CustomerProfileRepository;
import com.csc340.Swap_A_Bookaroo.repository.TagRepository;

@Service
public class CustomerProfileService {

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TagRepository tagRepository;

    public CustomerProfile createCustomerProfile(CustomerProfile profile) {
        if (profile.getAccount() != null) {
            profile.getAccount().setRole("CUSTOMER");
        }
        profile.setPreferences(new ArrayList<>());
        return customerProfileRepository.save(profile);
    }

    public List<CustomerProfile> getAllCustomers() {
        return customerProfileRepository.findAll();
    }

    public CustomerProfile getCustomerById(Long id) {
        return customerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Profile not found with id: " + id));
    }

    public CustomerProfile getCustomerByAccountId(Long accountId) {
        return customerProfileRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Customer Profile not found for account id: " + accountId));
    }

    public CustomerProfile updateCustomerProfile(Long id, CustomerProfile updatedProfile) {
        CustomerProfile existing = getCustomerById(id);
        if (updatedProfile.getAccount() != null && existing.getAccount() != null) {
            Account existingAcc = existing.getAccount();
            Account updatedAcc = updatedProfile.getAccount();
            existingAcc.setFirstName(updatedAcc.getFirstName());
            existingAcc.setLastName(updatedAcc.getLastName());
            existingAcc.setUsername(updatedAcc.getUsername());
            existingAcc.setPassword(updatedAcc.getPassword());
        }
        return customerProfileRepository.save(existing);
    }

    public void deleteCustomerProfile(Long id) {
        customerProfileRepository.deleteById(id);
    }

    public List<Tag> addPreferenceTag(Long customerProfileId, Tag tagData) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        Tag tag = tagRepository.findByTagName(tagData.getTagName())
                .orElseGet(() -> tagRepository.save(tagData));
        
        if (!profile.getPreferences().contains(tag)) {
            profile.getPreferences().add(tag);
            customerProfileRepository.save(profile);
        }
        return profile.getPreferences();
    }

    public List<Tag> getCustomerPreferences(Long customerProfileId) {
        return getCustomerById(customerProfileId).getPreferences();
    }

    public List<Tag> removePreferenceTag(Long customerProfileId, Long tagId) {
        CustomerProfile profile = getCustomerById(customerProfileId);
        profile.getPreferences().removeIf(tag -> tag.getId().equals(tagId));
        customerProfileRepository.save(profile);
        return profile.getPreferences();
    }
}
