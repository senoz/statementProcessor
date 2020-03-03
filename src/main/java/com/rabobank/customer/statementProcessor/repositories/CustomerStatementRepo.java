package com.rabobank.customer.statementProcessor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.customer.statementProcessor.dtos.CustomerStatement;

@Repository
public interface CustomerStatementRepo extends JpaRepository<CustomerStatement, Long> {

}
