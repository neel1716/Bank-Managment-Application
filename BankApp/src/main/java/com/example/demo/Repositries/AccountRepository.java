package com.example.demo.Repositries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	List<Account> findByAccountHolderIgnoreCase(String name); // 🩷 Case-insensitive search
	Optional<Account> findByAccountNumber(int accountNumber);
	
	}

