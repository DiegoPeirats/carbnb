package com.diego_peirats.carbnb.infrastructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.carbnb.application.service.TransactionServiceImpl;

import DTOs.TransactionDto;
import event.TransactionEvent;

@RestController
@RequestMapping("api/v1")
public class TransactionController {
	
	@Autowired
	private TransactionServiceImpl service;
	
	@PostMapping("/create")
	public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionEvent request) {
		return service.createTransaction(request);
	}
	
	@GetMapping("/getCustomerTransactionList/{id}")
	public ResponseEntity<List<TransactionDto>> getCustomerTransactionList(@PathVariable Long id){
		return null;
	}
	
	@GetMapping("/getOwnerTransactionList/{id}")
	public ResponseEntity<List<TransactionDto>> getOwnerTransactionList(@PathVariable Long id){
		return null;
	}
	
	@DeleteMapping("/deleteTransaction/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
		try {
			service.deleteTransaction(id);
			return ResponseEntity.accepted().build();
		}
		catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
