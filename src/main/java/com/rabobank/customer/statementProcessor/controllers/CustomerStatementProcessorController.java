package com.rabobank.customer.statementProcessor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.customer.statementProcessor.exceptionhandler.FormatFailureException;
import com.rabobank.customer.statementProcessor.models.CustomerStatement;
import com.rabobank.customer.statementProcessor.services.CustomerStatementService;
import com.rabobank.customer.statementProcessor.utils.Response;

@RestController
@RequestMapping("/customer")
public class CustomerStatementProcessorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerStatementProcessorController.class);
	
	@Autowired
	private CustomerStatementService cutomerService;
	
	@RequestMapping(value = "/process-statement", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Response> processStatement(@RequestBody CustomerStatement customerStatement) throws Exception {
		Response res;
		try {
			res = cutomerService.processStatement(customerStatement);
		} catch (Exception ex) {
			LOGGER.error("Process statement controller error" + ex.getMessage());
			throw new FormatFailureException();
		}
		LOGGER.info("Processing the customer statement ");
		return new ResponseEntity<Response>(res, HttpStatus.OK);
	}

}
