package com.csc340.Swap_A_Bookaroo.apiController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.Account;
import com.csc340.Swap_A_Bookaroo.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountApiController {

    private final AccountService accountService;

    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/me")
    public ResponseEntity<Account> getCurrentAccount(
            Authentication authentication) {
        Account account =
                accountService.getAccountByUsername(authentication.getName());
        return account != null
                ? ResponseEntity.ok(account)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Account> updateCurrentAccount(
            Authentication authentication,
            @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccountForUsername(
                authentication.getName(),
                updatedAccount);

        return account != null
                ? ResponseEntity.ok(account)
                : ResponseEntity.notFound().build();
    }
}