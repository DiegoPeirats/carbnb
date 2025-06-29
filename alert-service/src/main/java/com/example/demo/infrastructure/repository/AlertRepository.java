package com.example.demo.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long>{
	
	List<Alert> findAllByUserId(Long userId);

}
