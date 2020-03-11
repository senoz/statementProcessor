package com.rabobank.customer.statementProcessor.dtos;

public class CustomerStatement {
	
	private long transactionReference;
	
	private long accountNumber;
	
	private double startBalance;
	
	private double mutation;
	
	private String description;
	
	private double endBalance;

	public long getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(long transactionReference) {
		this.transactionReference = transactionReference;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	public double getMutation() {
		return mutation;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(double balance) {
		endBalance = balance;
	}

}
