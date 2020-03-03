package com.rabobank.customer.statementProcessor.utils;

public class ErrorMessage {

	public ErrorMessage(long reference, long accountNumber) {
		this.reference = reference;
		this.accountNumber = accountNumber;
	}
	
	private long reference;

	private long accountNumber;

	public long getReference() {
		return reference;
	}

	public void setReference(long reference) {
		this.reference = reference;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

}
