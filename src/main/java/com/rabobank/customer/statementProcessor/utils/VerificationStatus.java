package com.rabobank.customer.statementProcessor.utils;

public enum VerificationStatus {

	SUCCESSFUL,
	DUPLICATE_REFERENCE,
	INCORRECT_END_BALANCE,
	DUPLICATE_REFERENCE_INCORRECT_END_BALANCE,
	BAD_REQUEST,
	INTERNAL_SERVER_ERROR;
}
