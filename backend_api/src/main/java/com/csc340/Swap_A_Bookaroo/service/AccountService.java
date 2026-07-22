package com.csc340.Swap_A_Bookaroo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.entities.CustomerProfile;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.repository.AccountRepository;
import com.csc340.Swap_A_Bookaroo.repository.CustomerProfileRepository;
import com.csc340.Swap_A_Bookaroo.repository.ProviderProfileRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final ProviderProfileRepository providerProfileRepository;

    public AccountService(AccountRepository accountRepository, 
                          CustomerProfileRepository customerProfileRepository,
                          ProviderProfileRepository providerProfileRepository) {
        this.accountRepository = accountRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.providerProfileRepository = providerProfileRepository;
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsername(username)
                .filter(account -> account.getPassword().equals(password))
                .orElse(null);
    }

    public Account updateAccount(Long accountId, Account updatedAccount) {
        Account existingAccount = accountRepository.findById(accountId).orElse(null);
        if (existingAccount == null) return null;
        existingAccount.setFirstName(updatedAccount.getFirstName());
        existingAccount.setLastName(updatedAccount.getLastName());
        existingAccount.setUsername(updatedAccount.getUsername());
        existingAccount.setPassword(updatedAccount.getPassword());
        return accountRepository.save(existingAccount);
    }

    public boolean deleteAccount(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }

    @Transactional
    public Account registerDualAccount(Account account){
        // Check if username is in use
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null;
        }
        // Assign role
        account.setRole("USER");
        Account savedAccount = accountRepository.save(account);
        // Create Customer Profile
        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setAccount(savedAccount);
        customerProfileRepository.save(customerProfile);
        // Create Provider Profile
        ProviderProfile providerProfile = new ProviderProfile();
        providerProfile.setAccount(savedAccount);
        providerProfileRepository.save(providerProfile);

        return savedAccount;
    }
}
