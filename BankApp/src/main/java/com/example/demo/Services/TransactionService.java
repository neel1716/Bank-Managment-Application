package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Account;
import com.example.demo.Entities.Transaction;
import com.example.demo.Repositries.AccountRepository;
import com.example.demo.Repositries.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepo;
    private final AccountRepository accountRepo;

    public TransactionService(TransactionRepository transactionRepo, AccountRepository accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    public String deposit(int accountNumber, double amount) {

        Account acc = accountRepo.findById(accountNumber).orElse(null);
        if (acc == null) {
            return "Deposit failed: Account not found.";
        }

        acc.setBalance(acc.getBalance() + amount);
        accountRepo.save(acc);

        try {
            // 🟫 updated: Use Account object instead of account number
            Transaction txn = new Transaction("DEPOSIT", amount, acc);    
            transactionRepo.save(txn);
            System.out.println("Transaction saved: " + txn);
            System.out.println("All transactions in DB: " + transactionRepo.findAll());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Deposit successful: " + amount;
    }

    public String withdraw(int accountNumber, double amount) {
        Account acc = accountRepo.findById(accountNumber).orElse(null);
        if (acc == null || acc.getBalance() < amount) {
            return "Withdrawal failed: Insufficient balance or account not found.";
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepo.save(acc);

        // 🟫 updated: Use Account object instead of account number
        Transaction txn = new Transaction("WITHDRAW", amount, acc);
        transactionRepo.save(txn);

        return "Withdrawal successful: " + amount;
    }

    
    //TRANSFER
    public String transfer(int fromAccountNumber, int toAccountNumber, double amount) {
        Optional<Account> fromAccountOpt = accountRepo.findByAccountNumber(fromAccountNumber);
        Optional<Account> toAccountOpt = accountRepo.findByAccountNumber(toAccountNumber);

        if (fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) {
            return "One or both accounts not found";
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        if (fromAccount.getBalance() < amount) {
            return "Insufficient balance in source account";
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);

        // Save transaction records for both accounts
        transactionRepo.save(new Transaction("TRANSFER_OUT", amount, fromAccount));
        transactionRepo.save(new Transaction("TRANSFER_IN", amount, toAccount));

        return "Transfer successful";
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    // 🟨 ADDED: Get transactions by account number
    public List<Transaction> getTransactionsByAccount(int accountNumber)
    {
        return transactionRepo.findByAccount_AccountNumber(accountNumber);
    }

}
