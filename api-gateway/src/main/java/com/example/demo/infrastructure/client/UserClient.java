package com.example.demo.infrastructure.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import DTOs.UserDto;
import requests.LoginRequest;
import requests.UserActivationRequest;
import requests.UserCreationRequest;

@FeignClient(name="user-service", path="/user-app")
public interface UserClient {
	
	@PostMapping("/createUser")
	ResponseEntity<UserDto> createUser(@RequestBody UserCreationRequest request);

	@PostMapping("/getUser")
	ResponseEntity<UserDto> getUser(@RequestBody LoginRequest request);
	
	@GetMapping("/getStripeId/{id}")
	ResponseEntity<String> getStripeId(@PathVariable Long id);
	
	@PutMapping("/updateUser")
	ResponseEntity<UserDto> updateUser(@RequestBody UserCreationRequest request);
	
	@DeleteMapping("/deleteUser")
	ResponseEntity<Void> deleteUser(@RequestBody LoginRequest request);
	
	@PostMapping("/activateOwner")
	ResponseEntity<String> activateOwner(@RequestBody UserActivationRequest request);
	
	@PostMapping("/getUserByEmail")
	ResponseEntity<UserDto> getUserByEmail(@RequestBody String email);
	
	@GetMapping("/getUserById/{id}")
	ResponseEntity<UserDto> getUserById(@PathVariable Long id);
}
