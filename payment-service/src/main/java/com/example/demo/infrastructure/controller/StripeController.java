package com.example.demo.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.service.StripeService;
import com.stripe.exception.StripeException;

import DTOs.UserDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stripe")
@RequiredArgsConstructor
public class StripeController {
	
	private final StripeService service;
	
	@PostMapping("/customer")
	public ResponseEntity<String> createCustomer(@RequestBody UserDto user) {
		try {
			return ResponseEntity.ok(service.createCustomer(user));
		}catch(StripeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	@PostMapping("/owner")
	public ResponseEntity<String> createConnectAccount(@RequestBody UserDto user) {
		try {
			return ResponseEntity.ok(service.createConnectAccount(user));
		}catch(StripeException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
