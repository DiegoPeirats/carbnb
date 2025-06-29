package com.diego_peirats.carbnb.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import DTOs.TransactionDto;
import event.TransactionEvent;

public interface TransactionService {
	
	ResponseEntity<TransactionDto> createTransaction(TransactionEvent request);
	
	void deleteTransaction(Long id);
	
	ResponseEntity<List<TransactionDto>> getCustomerTransactionList(Long id);
	
	ResponseEntity<List<TransactionDto>> getOwnerTransactionList(Long id);

}
