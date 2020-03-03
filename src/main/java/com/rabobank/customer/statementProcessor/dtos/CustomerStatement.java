package com.rabobank.customer.statementProcessor.dtos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="CUSTOMER_STATEMENT")
public class CustomerStatement {
	
	@Id
	@Column(name="TRANSACTION_REFERENCE")
	private long transactionReference;
	
	@Column(name="ACCOUNT_NUMBER")
	private long accountNumber;
	
	@Column(name="START_BALANCE")
	private double startBalance;
	
	@Column(name="MUTATION")
	private double mutation;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="END_BALANCE")
	private double EndBalance;

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
		return EndBalance;
	}

	public void setEndBalance(double endBalance) {
		EndBalance = endBalance;
	}
	
	

}
