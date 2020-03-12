package com.rabobank.customer.statementProcessor.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabobank.customer.statementProcessor.models.CustomerStatement;
import com.rabobank.customer.statementProcessor.utils.ErrorMessage;
import com.rabobank.customer.statementProcessor.utils.Response;

@Service
public interface CustomerStatementService {

	public ResponseEntity<Response> processStatement(CustomerStatement cs) throws Exception;
	public void setResponseData(String result, List<ErrorMessage> errors) throws Exception;
	public void saveStatements(CustomerStatement statement) throws Exception;
	public boolean isTransactionReferenceExists(long transRef) throws Exception;
	public boolean checkEndBalance(double startBalance, double mutation, double endBalance) throws Exception;
	public String getIncorrectEndBalanceMessage() throws Exception;
	public String getSussessMessgae() throws Exception;
	public String getDuplicateReferenceMessage() throws Exception;
	public String getDuplicateReferenceWithInCorrectBalMessage() throws Exception;
}
