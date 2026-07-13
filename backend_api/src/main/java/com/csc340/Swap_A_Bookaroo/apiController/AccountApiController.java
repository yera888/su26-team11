package com.csc340.Swap_A_Bookaroo.apiController;

import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account loginRequest) {
        Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.status(401).build();
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(accountId, updatedAccount);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        return accountService.deleteAccount(accountId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}