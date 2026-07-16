package com.csc340.Swap_A_Bookaroo.service;

import org.springframework.stereotype.Service;
import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
}
