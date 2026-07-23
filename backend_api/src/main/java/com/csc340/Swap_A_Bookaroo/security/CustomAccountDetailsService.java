package com.csc340.Swap_A_Bookaroo.security;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        // Split "CUSTOMER,PROVIDER" into individual SimpleGrantedAuthority objects
        List<SimpleGrantedAuthority> authorities = Arrays.stream(account.getRole().split(","))
                .map(String::trim)
                .filter(r -> !r.isEmpty())
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new User(
                account.getUsername(),
                account.getPassword(),
                authorities
        );
    }
}