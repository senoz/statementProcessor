package com.rabobank.customer.statementProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import javax.validation.ReportAsSingleViolation;

import org.h2.command.dml.MergeUsing.When;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rabobank.customer.statementProcessor.controllers.CustomerStatementProcessorController;
import com.rabobank.customer.statementProcessor.dtos.CustomerStatement;
import com.rabobank.customer.statementProcessor.repositories.CustomerStatementRepo;
import com.rabobank.customer.statementProcessor.serviceImpl.CustomerStatementServiceImpl;
import com.rabobank.customer.statementProcessor.services.CustomerStatementService;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StatementProcessorControllerTest {

//	@Autowired
//	private MockMvc mvc;
	@InjectMocks
	CustomerStatementServiceImpl serv;
	@Mock
	CustomerStatementRepo repo;

	private static Optional<CustomerStatement> ops;
	private static CustomerStatement cs;

	// Data
	static {
		cs = new CustomerStatement();
		cs.setAccountNumber(1111);
		cs.setTransactionReference(1111);
		cs.setStartBalance(1000);
		cs.setEndBalance(1050);
		ops = Optional.of(cs);
	}

	@Test
	public void ProcessStatementForDuplicateReference() throws Exception {
		cs.setMutation(50);
		when(repo.findById((long) 1111)).thenReturn(ops);
		Response responseEntity = serv.processStatement(cs).getBody();
		assertThat(responseEntity.getResult()).isEqualTo(VerificationStatus.DUPLICATE_REFERENCE.toString());
	}

	@Test
	public void ProcessStatementForIncorrectBalance() throws Exception {
		cs.setMutation(101);
		when(repo.findById((long) 1111)).thenReturn(Optional.ofNullable(null));
		Response responseEntity = serv.processStatement(cs).getBody();
		assertThat(responseEntity.getResult())
				.isEqualTo(VerificationStatus.INCORRECT_END_BALANCE.toString());
	}

	@Test
	public void ProcessStatementForDuplicateReferenceAndIncorrectBalance() throws Exception {
		cs.setMutation(101);
		when(repo.findById((long) 1111)).thenReturn(ops);
		Response responseEntity = serv.processStatement(cs).getBody();
		assertThat(responseEntity.getResult())
				.isEqualTo(VerificationStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString());
	}

	@Test
	public void ProcessStatementForSuccess() throws Exception {
		cs.setMutation(50);
		when(repo.findById((long) 1111)).thenReturn(Optional.ofNullable(null));
		Response responseEntity = serv.processStatement(cs).getBody();
		assertThat(responseEntity.getResult()).isEqualTo(VerificationStatus.SUCCESSFUL.toString());
	}

}
