package com.example.demo.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	private String transactionType;

	private double amount;

	private LocalDateTime date;

	// 🔵 UPDATED: Remove raw accountNumber field and use relationship
	// private int accountNumber;

	// 🔵 UPDATED: Add many-to-one mapping to Account
	@ManyToOne(fetch = FetchType.LAZY) // Lazy is best practice
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
	private Account account; // 🔵 UPDATED

	public Transaction() {
	}

	// 🔵 UPDATED constructor to use Account
	public Transaction(String transactionType, double amount, Account account) {
		this.transactionType = transactionType;
		this.amount = amount;
		this.account = account;
		this.date = LocalDateTime.now();
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	// 🔵 UPDATED: Getter & Setter for Account
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Transaction{" + "transactionId=" + transactionId + ", transactionType='" + transactionType + '\''
				+ ", amount=" + amount + ", date=" + date + ", accountNumber="
				+ (account != null ? account.getAccountNumber() : null) + // 🔵 UPDATED
				'}';
	}
}
