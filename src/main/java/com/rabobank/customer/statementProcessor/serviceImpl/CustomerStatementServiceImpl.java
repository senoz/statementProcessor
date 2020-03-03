package com.rabobank.customer.statementProcessor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabobank.customer.statementProcessor.dtos.CustomerStatement;
import com.rabobank.customer.statementProcessor.repositories.CustomerStatementRepo;
import com.rabobank.customer.statementProcessor.services.CustomerStatementService;
import com.rabobank.customer.statementProcessor.utils.ErrorMessage;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {

	@Autowired
	CustomerStatementRepo repo;
	
	@Override
	public ResponseEntity<Response> processStatement(CustomerStatement cs) throws Exception {
		
		Response res = new Response();
		List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
		boolean isDuplicate = false;
		boolean isIncorrectBalance = false;
		
		try {
			if (repo.findById(cs.getTransactionReference()).isPresent()) {
				isDuplicate = true;
				errors.add(new ErrorMessage(cs.getTransactionReference(), cs.getAccountNumber()));
				res.setResult(VerificationStatus.DUPLICATE_REFERENCE.toString());
				res.setErrorRecords(errors);
			}
			if ((cs.getStartBalance() + cs.getMutation()) != cs.getEndBalance()) {
				isIncorrectBalance = true;
				errors.add(new ErrorMessage(cs.getTransactionReference(), cs.getAccountNumber()));
				res.setResult(VerificationStatus.INCORRECT_END_BALANCE.toString());
				res.setErrorRecords(errors);
			} 
			if (isDuplicate && isIncorrectBalance) {
				res.setResult(VerificationStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString());
				res.setErrorRecords(errors);
			}
			if (!isDuplicate && !isIncorrectBalance) {
				repo.save(cs);
				res.setResult(VerificationStatus.SUCCESSFUL.toString());
			}
		} catch (Exception ex) {
			throw new Exception();
		}
		return new ResponseEntity<Response>(res, HttpStatus.OK);
	}
}
