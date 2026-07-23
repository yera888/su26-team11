package com.csc340.Swap_A_Bookaroo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public Account registerAccount(Account account, String role) {
        if (account == null
                || account.getUsername() == null
                || account.getPassword() == null
                || role == null) {
            return null;
        }

        String normalizedUsername = account.getUsername().trim();
        if (normalizedUsername.isBlank()
                || account.getPassword().isBlank()
                || accountRepository.existsByUsername(normalizedUsername)) {
            return null;
        }

        account.setUsername(normalizedUsername);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(role.toUpperCase());
        return accountRepository.save(account);
    }

    // Helper method to grant an additional role to an existing account
    @Transactional
    public Account addRoleToAccount(Account account, String newRole) {
        if (account == null || newRole == null || newRole.isBlank()) {
            return account;
        }
        
        String upperRole = newRole.trim().toUpperCase();
        if (account.getRole() == null || account.getRole().isBlank()) {
            account.setRole(upperRole);
        } else if (!account.getRole().contains(upperRole)) {
            account.setRole(account.getRole() + "," + upperRole);
        }
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Long accountId, Account updatedAccount) {
        Account existingAccount = accountRepository.findById(accountId).orElse(null);
        if (existingAccount == null || updatedAccount == null) {
            return null;
        }

        applyUpdates(existingAccount, updatedAccount);
        return accountRepository.save(existingAccount);
    }

    @Transactional
    public Account updateAccountForUsername(String username, Account updatedAccount) {
        Account existingAccount = accountRepository.findByUsername(username).orElse(null);
        if (existingAccount == null || updatedAccount == null) {
            return null;
        }

        applyUpdates(existingAccount, updatedAccount);
        return accountRepository.save(existingAccount);
    }

    @Transactional
    public boolean deleteAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            return false;
        }
        accountRepository.deleteById(accountId);
        return true;
    }

    private void applyUpdates(Account existingAccount, Account updatedAccount) {
        if (updatedAccount.getFirstName() != null && !updatedAccount.getFirstName().isBlank()) {
            existingAccount.setFirstName(updatedAccount.getFirstName().trim());
        }

        if (updatedAccount.getLastName() != null && !updatedAccount.getLastName().isBlank()) {
            existingAccount.setLastName(updatedAccount.getLastName().trim());
        }

        if (updatedAccount.getUsername() != null
                && !updatedAccount.getUsername().isBlank()
                && !updatedAccount.getUsername().equals(existingAccount.getUsername())) {
            String requestedUsername = updatedAccount.getUsername().trim();
            if (!accountRepository.existsByUsername(requestedUsername)) {
                existingAccount.setUsername(requestedUsername);
            }
        }

        if (updatedAccount.getPassword() != null && !updatedAccount.getPassword().isBlank()) {
            existingAccount.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
        }
    }
}