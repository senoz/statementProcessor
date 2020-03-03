package com.rabobank.customer.statementProcessor.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rabobank.customer.statementProcessor.utils.ErrorMessage;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	Response res = new Response();
	List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
	
	@ExceptionHandler(FormatFailureException.class)
	public ResponseEntity<Response> handleBadRequestException() {
		res.setResult(VerificationStatus.BAD_REQUEST.toString());
		res.setErrorRecords(errors);
		return new ResponseEntity<Response>(res, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleInternalServerException() {
		res.setResult(VerificationStatus.INTERNAL_SERVER_ERROR.toString());
		res.setErrorRecords(errors);
		return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
