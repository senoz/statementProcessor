package com.rabobank.customer.statementProcessor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rabobank.customer.statementProcessor.models.CustomerStatement;
import com.rabobank.customer.statementProcessor.services.CustomerStatementService;
import com.rabobank.customer.statementProcessor.utils.ErrorMessage;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerStatementServiceImpl.class);

	List<CustomerStatement> customerStatement;
	Response res = new Response();

	public CustomerStatementServiceImpl() {
		this.customerStatement = new ArrayList<CustomerStatement>();
	}

	@Override
	public Response processStatement(CustomerStatement cs) throws Exception {
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
			LOGGER.error("Process statement Services error" + ex.getMessage());
			throw new Exception();
		}
		return this.res;
	}

	private void setResponseData(String result, List<ErrorMessage> errors) throws Exception {
		try {
		this.res.setResult(result);
		this.res.setErrorRecords(errors);
		} catch (Exception ex) {
			LOGGER.error("Response data adding error" + ex.getMessage());
			throw new Exception();
		}
	}

	private void saveStatements(CustomerStatement statement) throws Exception {
		try {
			this.customerStatement.add(statement);
		} catch (Exception ex) {
			LOGGER.error("save statements error" + ex.getMessage());
			throw new Exception();
		}
	}

	private boolean isTransactionReferenceExists(long transRef) throws Exception {
		boolean returnData = false;
		try {
			boolean isTransactionExists = customerStatement.stream()
					.filter(data -> (data.getTransactionReference() == transRef)).findFirst().isPresent();
			if (isTransactionExists) {
				returnData = true;
			}
		} catch (Exception ex) {
			LOGGER.error("Transaction Reference checking error" + ex.getMessage());
			throw new Exception();
		}
		return returnData;
	}

	private boolean checkEndBalance(double startBalance, double mutation, double endBalance) throws Exception {
		boolean returnData = false;
		try {
			if ((startBalance + mutation) != endBalance) {
				returnData = true;
			}
		} catch (Exception ex) {
			LOGGER.error("End Balance verification error" + ex.getMessage());
			throw new Exception();
		}
		return returnData;
	}

	private String getIncorrectEndBalanceMessage() throws Exception {
		return VerificationStatus.INCORRECT_END_BALANCE.toString();
	}

	private String getSussessMessgae() throws Exception {
		return VerificationStatus.SUCCESSFUL.toString();
	}

	private String getDuplicateReferenceMessage() throws Exception {
		return VerificationStatus.DUPLICATE_REFERENCE.toString();
	}

	private String getDuplicateReferenceWithInCorrectBalMessage() throws Exception {
		return VerificationStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString();
	}
}
