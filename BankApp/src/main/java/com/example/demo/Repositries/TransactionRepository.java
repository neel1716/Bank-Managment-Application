package com.example.demo.Repositries;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // ✅ This enables you to find all transactions by Account
	List<Transaction> findByAccount_AccountNumber(int accountNumber);
}
