package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entities.Transaction;
import com.example.demo.Services.TransactionService;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 🔴 UPDATED: Now using request.getAccount().getAccountNumber()
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam int accountNumber, @RequestParam double amount) {
        transactionService.deposit(accountNumber, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    // 🔴 UPDATED: Now using request.getAccount().getAccountNumber()
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam int accountNumber, @RequestParam double amount) {
        transactionService.withdraw(accountNumber, amount);
        return ResponseEntity.ok("Withdrawal successful");
    }


    // ✅ Get all transactions
    @GetMapping("")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // ✅ Get transactions by account number
    @GetMapping("account/{accountNumber}")
    public List<Transaction> getTransactionsByAccount(@PathVariable int accountNumber) {
        return transactionService.getTransactionsByAccount(accountNumber);
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam int fromAccountNumber,
            @RequestParam int toAccountNumber,
            @RequestParam double amount) {
        String result = transactionService.transfer(fromAccountNumber, toAccountNumber, amount);
        return ResponseEntity.ok(result);
    }

}
