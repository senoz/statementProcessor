package com.rabobank.customer.statementProcessor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabobank.customer.statementProcessor.models.CustomerStatement;
import com.rabobank.customer.statementProcessor.services.CustomerStatementService;
import com.rabobank.customer.statementProcessor.utils.ErrorMessage;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {

	List<CustomerStatement> customerStatement;
	Response res = new Response();

	public CustomerStatementServiceImpl() {
		this.customerStatement = new ArrayList<CustomerStatement>();
	}

	@Override
	public ResponseEntity<Response> processStatement(CustomerStatement cs) throws Exception {
		boolean isDuplicate = false;
		boolean isIncorrectBalance = false;

		List<ErrorMessage> errors = new ArrayList<ErrorMessage>();

		try {
			if (checkEndBalance(cs.getStartBalance(), cs.getMutation(), cs.getEndBalance())) {
				isIncorrectBalance = true;
				errors.add(new ErrorMessage(cs.getTransactionReference(), cs.getAccountNumber()));
				setResponseData(this.getIncorrectEndBalanceMessage(), errors);
			}
			if (isTransactionReferenceExists(cs.getTransactionReference())) {
				isDuplicate = true;
				errors.add(new ErrorMessage(cs.getTransactionReference(), cs.getAccountNumber()));
				setResponseData(this.getDuplicateReferenceMessage(), errors);
			}
			if (isDuplicate && isIncorrectBalance) {
				setResponseData(this.getDuplicateReferenceWithInCorrectBalMessage(), errors);
			}
			if (!isDuplicate && !isIncorrectBalance) {
				saveStatements(cs);
				setResponseData(this.getSussessMessgae(), errors);
			}
		} catch (Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<Response>(this.res, HttpStatus.OK);
	}

	@Override
	public void setResponseData(String result, List<ErrorMessage> errors) throws Exception {
		this.res.setResult(result);
		this.res.setErrorRecords(errors);
	}

	@Override
	public void saveStatements(CustomerStatement statement) throws Exception {
		this.customerStatement.add(statement);
	}

	@Override
	public boolean isTransactionReferenceExists(long transRef) throws Exception {
		boolean returnData = false;
		boolean isTransactionExists = customerStatement.stream()
				.filter(data -> (data.getTransactionReference() == transRef)).findFirst().isPresent();
		if (isTransactionExists) {
			returnData = true;
		}
		return returnData;
	}

	@Override
	public boolean checkEndBalance(double startBalance, double mutation, double endBalance) throws Exception {
		boolean returnData = false;
		if ((startBalance + mutation) != endBalance) {
			returnData = true;
		}
		return returnData;
	}
	
	@Override
	public String getIncorrectEndBalanceMessage() throws Exception {
		return VerificationStatus.INCORRECT_END_BALANCE.toString();
	}
	
	@Override
	public String getSussessMessgae() throws Exception {
		return VerificationStatus.SUCCESSFUL.toString();
	}
	
	@Override
	public String getDuplicateReferenceMessage() throws Exception {
		return VerificationStatus.DUPLICATE_REFERENCE.toString();
	}
	
	@Override
	public String getDuplicateReferenceWithInCorrectBalMessage() throws Exception {
		return VerificationStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString();
	}
}
