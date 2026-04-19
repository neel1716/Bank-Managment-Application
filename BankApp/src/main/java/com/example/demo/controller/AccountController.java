package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;               // 🔵 Dependency injection
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repositries.AccountRepository;
import com.example.demo.Entities.Account;
import com.example.demo.Exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*") // ✅ Allow all origins — safe for development
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;                          // 🔵 Repository used

    // 🟤 Get account by ID (clean version)
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID " + id + " not found"));
        return ResponseEntity.ok(acc);
    }

    // ✅ Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();                               // 🔵 Fetch all using JPA
    }

    // ✅ Search accounts by name
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Account>> searchByName(@PathVariable String name) {
        List<Account> accounts = accountRepository.findByAccountHolderIgnoreCase(name);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(accounts);           // 200 with data
    }


    // ✅ Update account
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable int id, @Valid @RequestBody Account updatedAccount) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID " + id + " not found")); // 🟤 Updated Exception

        if (updatedAccount.getBalance() < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }

        existingAccount.setAccountHolder(updatedAccount.getAccountHolder());
        existingAccount.setBalance(updatedAccount.getBalance());

        return accountRepository.save(existingAccount);                   // 🔵 Save updated entity
    }

    // ✅ Add new account
    @PostMapping("/api/accounts")

    public Account addAccount(@Valid @RequestBody Account newAccount) {
        if (newAccount.getBalance() < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        return accountRepository.save(newAccount);                        // 🔵 Persist new account
    }
    
    @PostMapping("")
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    // ✅ Delete account
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable int id) {
        Account removed = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID " + id + " not found")); // 🟤 Updated Exception
        accountRepository.delete(removed);
        return "Account with ID " + id + " has been deleted.";
    }
}
