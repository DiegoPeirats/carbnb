package com.example.demo.infrastructure.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.StripeEvent;

@Repository
public interface StripeEventRepository extends JpaRepository<StripeEvent, String>{
	

}
