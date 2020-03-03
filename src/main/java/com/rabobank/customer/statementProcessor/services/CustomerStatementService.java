package com.rabobank.customer.statementProcessor.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabobank.customer.statementProcessor.dtos.CustomerStatement;
import com.rabobank.customer.statementProcessor.utils.Response;

@Service
public interface CustomerStatementService {

	public ResponseEntity<Response> processStatement(CustomerStatement cs) throws Exception;
}
