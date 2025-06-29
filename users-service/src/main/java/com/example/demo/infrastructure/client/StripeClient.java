package com.example.demo.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import DTOs.UserDto;

@FeignClient(name="stripe-client", path="/stripe-client")
public interface StripeClient {
	
	@PostMapping("/customer")
	public ResponseEntity<String> createCustomer(@RequestBody UserDto user);
	
	@PostMapping("/owner")
	public ResponseEntity<String> createConnectAccount(@RequestBody UserDto user);
}