package com.example.demo.infrastructure.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import DTOs.TransactionDto;

@FeignClient(name="transaction-client", path="/transaction-client")
public interface TransactionClient {
	
	@GetMapping("/getCustomerTransactionList/{id}")
	public ResponseEntity<List<TransactionDto>> getCustomerTransactionList(@PathVariable Long id);
	
	@GetMapping("/getOwnerTransactionList/{id}")
	public ResponseEntity<List<TransactionDto>> getOwnerTransactionList(@PathVariable Long id);

}
