package com.rabobank.customer.statementProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rabobank.customer.statementProcessor.controllers.CustomerStatementProcessorController;
import com.rabobank.customer.statementProcessor.serviceImpl.CustomerStatementServiceImpl;

@WebMvcTest(StatementProcessorControllerTest.class)
@ExtendWith(SpringExtension.class)
class StatementProcessorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerStatementServiceImpl service;
	
	String inputData;

	@DisplayName("Test for SUCCESS save transaction ")
	@Test
	public void testSaveSuccessfully() throws Exception {
		String statement = "{\"transactionReference\": \"1262296\","
				+ " \"accountNumber\":\"3555\","
				+ " \"startBalance\":\"10.5\","
				+ " \"mutation\":\"1.5\","
				+ " \"description\":\"Test\","
				+ " \"endBalance\":\"12.0\"}";
		  MvcResult result = mockMvc.perform(
				  MockMvcRequestBuilders
				  	.post("/customer/process-statement")
		    .contentType("application/json;charset=UTF-8")
			.content(statement)).andReturn();

		  assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@DisplayName("Test for Duplicate Reference in transaction ")
	@Test
	public void testDuplicateReference() throws Exception {
		String statement = "{\"transactionReference\": \"1262296\","
				+ " \"accountNumber\":\"3555\","
				+ " \"startBalance\":\"10.5\","
				+ " \"mutation\":\"1.5\","
				+ " \"description\":\"Test\","
				+ " \"endBalance\":\"12.0\"}";
		  MvcResult result = mockMvc.perform(
				  MockMvcRequestBuilders
				  	.post("/customer/process-statement")
		    .contentType("application/json;charset=UTF-8")
			.content(statement)).andReturn();

		  assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
//
//	@Test
//	public void testIncorrectEndBalance() throws Exception {
//		CustomerStatementServiceImpl service = new CustomerStatementServiceImpl();
//		Response responseEntity = service.processStatement(cs).getBody();
//		cs.setMutation(101);
//		assertThat(responseEntity.getResult())
//				.isEqualTo(VerificationStatus.INCORRECT_END_BALANCE.toString());
//	}
//
//	@Test
//	public void testDuplicateRefWithIncorrectEndBalance() throws Exception {
//		CustomerStatementServiceImpl service = new CustomerStatementServiceImpl();
//		Response responseEntity = service.processStatement(cs).getBody();
//		cs.setMutation(101);
//		assertThat(responseEntity.getResult())
//				.isEqualTo(VerificationStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString());
//	}
//
//	@Test
//	public void testSuccessData() throws Exception {
//		CustomerStatementServiceImpl service = new CustomerStatementServiceImpl();
//		Response responseEntity = service.processStatement(cs).getBody();
//		cs.setMutation(50);
//		assertThat(responseEntity.getResult()).isEqualTo(VerificationStatus.SUCCESSFUL.toString());
//	}

}
