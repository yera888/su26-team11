package com.csc340.Swap_A_Bookaroo.apiController;

import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

=======
import org.springframework.web.bind.annotation.*;
>>>>>>> origin/main
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
<<<<<<< HEAD
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
=======
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
>>>>>>> origin/main
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account loginRequest) {
        Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
<<<<<<< HEAD
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.status(401).build();
=======
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.status(401).build();
>>>>>>> origin/main
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(accountId, updatedAccount);
<<<<<<< HEAD
        if (account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
=======
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
>>>>>>> origin/main
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
<<<<<<< HEAD
        boolean deleted = accountService.deleteAccount(accountId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
=======
        return accountService.deleteAccount(accountId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
>>>>>>> origin/main
