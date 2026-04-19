package com.example.demo.Exceptions;

public class AccountNotFoundException extends RuntimeException
{
	public AccountNotFoundException(int id)
	{
		super("Account with ID" +id+ "not found");
	}

}
