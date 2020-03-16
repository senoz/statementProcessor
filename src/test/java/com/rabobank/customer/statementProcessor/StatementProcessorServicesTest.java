package com.rabobank.customer.statementProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rabobank.customer.statementProcessor.models.CustomerStatement;
import com.rabobank.customer.statementProcessor.serviceImpl.CustomerStatementServiceImpl;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatementProcessorServicesTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatementProcessorServicesTest.class);

	@Autowired
	CustomerStatementServiceImpl service;

	@DisplayName("Test for Process statement method service Response as SUCCESSFUL")
	@Test
	public void testProcessStatement1() throws Exception {
		try {
			CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 10.5, 1.5, "Tests", 12.0);
			Response result = service.processStatement(mockStatement);
			assertEquals(VerificationStatus.SUCCESSFUL.toString(), result.getResult());
		} catch (Exception ex) {
			LOGGER.error("test SUCCESSFUL service error" + ex.getMessage());
			throw new Exception();
		}
	}

	@DisplayName("Test for Process statement method service Response as DUPLICATE_REFERENCE")
	@Test
	public void testProcessStatement2() throws Exception {
		try {
			CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 10.5, 1.5, "Tests", 12.0);
			Response result = service.processStatement(mockStatement);
			assertEquals(VerificationStatus.DUPLICATE_REFERENCE.toString(), result.getResult());
		} catch (Exception ex) {
			LOGGER.error("test DUPLICATE_REFERENCE service error" + ex.getMessage());
			throw new Exception();
		}
	}

	@DisplayName("Test for Process statement method service Response as INCORRECT_END_BALANCE")
	@Test
	public void testProcessStatement3() throws Exception {
		try {
			CustomerStatement mockStatement = new CustomerStatement(111111, 3555, 10.5, 1.5, "Tests", 120.0);
			Response result = service.processStatement(mockStatement);
			assertEquals(VerificationStatus.INCORRECT_END_BALANCE.toString(), result.getResult());
		} catch (Exception ex) {
			LOGGER.error("test INCORRECT_END_BALANCE service error" + ex.getMessage());
			throw new Exception();
		}
	}

	@DisplayName("Test for Process statement method service Response as DUPLICATE_REFERENCE_INCORRECT_END_BALANCE")
	@Test
	public void testProcessStatement4() throws Exception {
		try {
			CustomerStatement mockStatement = new CustomerStatement(1262296, 3555, 10.5, 1.5, "Tests", 120.0);
			Response result = service.processStatement(mockStatement);
			assertEquals(VerificationStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString(), result.getResult());
		} catch (Exception ex) {
			LOGGER.error("test DUPLICATE_REFERENCE_INCORRECT_END_BALANCE service error" + ex.getMessage());
			throw new Exception();
		}
	}
}
