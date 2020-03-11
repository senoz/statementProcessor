package com.rabobank.customer.statementProcessor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.rabobank.customer.statementProcessor.models.CustomerStatement;
import com.rabobank.customer.statementProcessor.serviceImpl.CustomerStatementServiceImpl;
import com.rabobank.customer.statementProcessor.utils.ErrorMessage;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class StatementProcessorServicesTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerStatementServiceImpl service;

	Gson gson = new Gson();

	@DisplayName("Test for Process statement method service")
	@Test
	public void testProcessStatement() throws Exception {
		CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 10.5, 1.5, "Tests", 12.0);
		List<ErrorMessage> errMess = new ArrayList<ErrorMessage>();
		Response mockRes = new Response(VerificationStatus.SUCCESSFUL.toString(), errMess);
		ResponseEntity<Response> mockResponse = new ResponseEntity<>(mockRes, HttpStatus.OK);
		Mockito.when(service.processStatement(Mockito.any(CustomerStatement.class))).thenReturn(mockResponse);
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/process-statement").content(gson.toJson(mockStatement))
				.contentType("application/json;charset=UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@DisplayName("Test for transaction exits method service")
	@Test
	public void testIsTransactionReferenceExists() throws Exception {
		assertTrue(service.isTransactionReferenceExists(1262296));
	}

	@DisplayName("Test for transaction not exits method service")
	@Test
	public void testIsNotTransactionReferenceExists() throws Exception {
		assertFalse(service.isTransactionReferenceExists(1111));
	}

	@DisplayName("Test for check the end balance method service as True")
	@Test
	public void testEqualCheckEndBalance() throws Exception {
		assertTrue(service.checkEndBalance(10.0, 1.0, 11.0));
	}

	@DisplayName("Test for check the end balance method service as False")
	@Test
	public void testNotEqualEndBalance() throws Exception {
		assertFalse(service.checkEndBalance(1.5, 1.5, 12.0));
	}

	@DisplayName("Test for save statement method service")
	@Test
	public void testSaveStatements() throws Exception {
		CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 10.5, 1.5, "Tests", 12.0);
		Mockito.doThrow(new Exception()).doNothing().when(service).saveStatements(mockStatement);
	}
}
