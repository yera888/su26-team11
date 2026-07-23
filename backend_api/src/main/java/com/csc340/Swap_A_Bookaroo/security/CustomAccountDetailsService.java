package com.csc340.Swap_A_Bookaroo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.repository.AccountRepository;

@Service
public class CustomAccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomAccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));

        if (account.getRole() == null || account.getRole().isBlank()) {
            throw new UsernameNotFoundException("Account has no assigned role");
        }

        String role = account.getRole().trim().toUpperCase();

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(role)
                .build();
    }
}