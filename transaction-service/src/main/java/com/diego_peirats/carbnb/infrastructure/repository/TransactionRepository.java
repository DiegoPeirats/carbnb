package com.diego_peirats.carbnb.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego_peirats.carbnb.domain.entity.Transaction;

import DTOs.TransactionDto;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findAllByCustomerId(Long id);
	
	List<Transaction> findAllByOwnerId(Long id);

}
