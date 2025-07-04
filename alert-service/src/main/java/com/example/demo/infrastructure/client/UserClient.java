package com.example.demo.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service", path="/user-app")
public interface UserClient {
	
	@GetMapping("/getEmail/{id}")
	ResponseEntity<String> getEmail(@PathVariable Long id);
	

}

