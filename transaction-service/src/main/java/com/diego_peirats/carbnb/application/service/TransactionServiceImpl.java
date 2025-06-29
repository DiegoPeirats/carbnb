package com.diego_peirats.carbnb.application.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diego_peirats.carbnb.domain.entity.Transaction;
import com.diego_peirats.carbnb.domain.service.TransactionService;
import com.diego_peirats.carbnb.infrastructure.listener.TransactionListener;
import com.diego_peirats.carbnb.infrastructure.repository.TransactionRepository;

import DTOs.TransactionDto;
import event.TransactionEvent;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private TransactionListener listener;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<TransactionDto> createTransaction(TransactionEvent request) {
		
		Transaction transaction = Transaction.builder()
				.customerId(request.getCustomerId())
				.ownerId(request.getOwnerId())
				.vehicleId(request.getVehicleId())
				.totalPrice(request.getTotalPrice())
				.build();
		
		Transaction saved = repository.save(transaction);
		
		return ResponseEntity.ok(modelMapper.map(saved, TransactionDto.class));
	}

	@Override
	public void deleteTransaction(Long id) {
		repository.findById(id)
		.ifPresent(repository :: delete);
		
	}

	@Override
	public ResponseEntity<List<TransactionDto>> getCustomerTransactionList(Long id) {
	    return getTransactionList(repository.findAllByCustomerId(id));
	}

	@Override
	public ResponseEntity<List<TransactionDto>> getOwnerTransactionList(Long id) {
	    return getTransactionList(repository.findAllByOwnerId(id));
	}

	private ResponseEntity<List<TransactionDto>> getTransactionList(List<Transaction> list) {
	    if (list.isEmpty()) return ResponseEntity.notFound().build();

	    List<TransactionDto> dtoList = list.stream()
	        .map(trans -> modelMapper.map(trans, TransactionDto.class))
	        .toList();

	    return ResponseEntity.ok(dtoList);
	}


}
