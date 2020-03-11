package com.rabobank.customer.statementProcessor.utils;

import java.util.List;

public class Response {
	
	private String result;
	
	private List<ErrorMessage> errorRecords;

	public Response() {
	}
	
	public Response(String res, List<ErrorMessage> rec) { 
		this.result = res;
		this.errorRecords = rec;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<ErrorMessage> getErrorRecords() {
		return errorRecords;
	}

	public void setErrorRecords(List<ErrorMessage> errorRecords) {
		this.errorRecords = errorRecords;
	}
}
