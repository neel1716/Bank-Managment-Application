package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="ACCOUNT")
public class Account {
	

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="ACCOUNT_ID")// Let DB handle ID
		private int accountId;

	  //  @Min(value = 1, message = "Account ID must be greater than 0") // 🩷 Validation: Account ID should be positive
//	    @OneToMany(mappedBy="transactionId")
		@Column(name="ACCOUNT_NUMBER")// Let DB handle ID
	   	private int accountNumber;

	   // @NotNull(message = "Account holder name is required") // 🩷 Validation: Not null
	   // @Size(min = 3, message = "Name must be at least 3 characters") // 🩷 Validation: Min length
	   	@JsonProperty("name") // maps "name" in JSON to this field
	   	@Column(name = "account_holder")
	    private String accountHolder;

	   // @Min(value = 0, message = "Balance must be non-negative") // 🩷 Validation: Balance should be non-negative
	    private double balance;

	    public Account() {  // Default constructor
	    }

	    public Account(String accountHolder, double balance,int accountNumber) {
	        
	        this.accountHolder = accountHolder;
	        this.balance = balance;
	        this.accountNumber = accountNumber;
	    }
	    
	    // 🩷 Getters & Setters
	    public int getAccountNumber() {
	        return accountNumber;
	    }

	    public void setAccountNumber(int accountNumber) {
	        this.accountNumber = accountNumber;
	    }

	    public int getAccountId() {
	        return accountId;
	    }

	    public void setAccountId(int accountId) {
	        this.accountId = accountId;
	    }

	    public String getAccountHolder() {
	        return accountHolder;
	    }

	    public void setAccountHolder(String accountHolder) {
	        this.accountHolder = accountHolder;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public void setBalance(double balance) {
	        this.balance = balance;
	    }

	    @Override
	    public String toString() {
	        return "Account{" +
	                "accountId=" + accountId +
	                ", accountHolder='" + accountHolder + '\'' +
	                ", balance=" + balance +
	                '}';
	    }


}
