package com.rabobank.customer.statementProcessor;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class StatementProcessorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerStatementServiceImpl service;

	Gson gson = new Gson();

	@DisplayName("Test for SUCCESS save transaction API call")
	@Test
	public void testSaveSuccessfully() throws Exception {
		CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 10.5, 1.5, "Tests", 12.0);
		List<ErrorMessage> errMess = new ArrayList<ErrorMessage>();
		Response mockRes = new Response(VerificationStatus.SUCCESSFUL.toString(), errMess);
		ResponseEntity<Response> mockResponse = new ResponseEntity<>(mockRes, HttpStatus.OK);
		Mockito.when(service.processStatement(Mockito.any(CustomerStatement.class))).thenReturn(mockResponse);
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/process-statement").content(gson.toJson(mockStatement))
				.contentType("application/json;charset=UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@DisplayName("Test for Duplicate Reference in transaction API call")
	@Test
	public void testDuplicateReference() throws Exception {
		CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 10.5, 1.5, "Tests", 12.0);
		List<ErrorMessage> errMess = new ArrayList<ErrorMessage>();
		Response mockRes = new Response(VerificationStatus.DUPLICATE_REFERENCE.toString(), errMess);
		ResponseEntity<Response> mockResponse = new ResponseEntity<>(mockRes, HttpStatus.OK);
		Mockito.when(service.processStatement(Mockito.any(CustomerStatement.class))).thenReturn(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/customer/process-statement").content(gson.toJson(mockStatement))
				.contentType("application/json;charset=UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@DisplayName("Test for Incorrect end balance transaction API call")
	@Test
	public void testIncorrectEndBalance() throws Exception {
		CustomerStatement mockStatement = new CustomerStatement(1262, 3555, 1.5, 1.5, "Tests", 12.0);
		List<ErrorMessage> errMess = new ArrayList<ErrorMessage>();
		Response mockRes = new Response(VerificationStatus.INCORRECT_END_BALANCE.toString(), errMess);
		ResponseEntity<Response> mockResponse = new ResponseEntity<>(mockRes, HttpStatus.OK);
		Mockito.when(service.processStatement(Mockito.any(CustomerStatement.class))).thenReturn(mockResponse);
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/process-statement").content(gson.toJson(mockStatement))
				.contentType("application/json;charset=UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@DisplayName("Test for Duplicate Reference & Incorrect end balance transaction API call")
	@Test
	public void testDuplicateReferenceWithIncorrectEndBalance() throws Exception {
		CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 1.5, 1.5, "Tests", 12.0);
		List<ErrorMessage> errMess = new ArrayList<ErrorMessage>();
		Response mockRes = new Response(VerificationStatus.INCORRECT_END_BALANCE.toString(), errMess);
		ResponseEntity<Response> mockResponse = new ResponseEntity<>(mockRes, HttpStatus.OK);
		Mockito.when(service.processStatement(Mockito.any(CustomerStatement.class))).thenReturn(mockResponse);
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/process-statement").content(gson.toJson(mockStatement))
				.contentType("application/json;charset=UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
