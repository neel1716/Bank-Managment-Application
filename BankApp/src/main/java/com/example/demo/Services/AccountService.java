package com.example.demo.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Account;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Repositries.AccountRepository;
@Service
public class AccountService
{
	 private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
    private AccountRepository accountRepository;

    // ✅ 1. Create a new account
    public Account createAccount(Account account) {
        // Set initial balance to 0 if not provided
        if (account.getBalance() < 0) {
            
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        Account saved = accountRepository.save(account);
        logger.info("Created account: {}", saved.getAccountNumber());
        
        return accountRepository.save(account);
    }
    public Account updateAccount(int id, Account updatedAccount) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID " + id + " not found"));

        existingAccount.setAccountHolder(updatedAccount.getAccountHolder());
        existingAccount.setBalance(updatedAccount.getBalance());

        return accountRepository.save(existingAccount);
    }


    // ✅ 2. Get account details by account number
    public Optional<Account> getAccountDetails(int accountNumber) {
    	logger.info("Fetching details for account: {}", accountNumber);
        return accountRepository.findById(accountNumber);
        
    }

    // ✅ 3. Close an account by removing it
    public String closeAccount(int accountNumber) {
        Optional<Account> acc = accountRepository.findById(accountNumber);
        if (acc.isPresent()) {
            accountRepository.deleteById(accountNumber);
            logger.info("Closed account: {}", accountNumber);
            return "Account " + accountNumber + " has been closed.";
        } else {
        	logger.warn("Attempted to close non-existent account: {}", accountNumber);
            return "Account " + accountNumber + " not found.";
        }
    }
}


