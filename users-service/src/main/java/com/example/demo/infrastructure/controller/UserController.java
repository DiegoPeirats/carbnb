package com.example.demo.infrastructure.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.application.service.UserServiceImpl;

import DTOs.UserDto;
import requests.LoginRequest;
import requests.UserActivationRequest;
import requests.UserCreationRequest;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserServiceImpl service;
	
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@RequestBody UserCreationRequest request) {
		 URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .buildAndExpand() 
	                .toUri();
		return service.createUser(request, location);
	}
	
	@PostMapping("/getUser")
	public ResponseEntity<UserDto> getUser(@RequestBody LoginRequest request){
		return service.getUser(request);
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserCreationRequest request){
		return service.updateUser(request);
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<Void> deleteUser(@RequestBody LoginRequest request) {
		try {
			service.deleteUser(request);
			return ResponseEntity.accepted().build();
		}
		catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<String> activateOwner(@RequestBody UserActivationRequest request){
		return service.activateOwnership(request);
	}
	
	@GetMapping("/getEmail/{id}")
	public ResponseEntity<String> getEmail(@PathVariable Long id){
		return service.getEmail(id);
	}
	
	@GetMapping("/getStripeId/{id}")
	public ResponseEntity<String> getStripeId(@PathVariable Long id){
		return service.getStripeId(id);
	}
	
	@PostMapping("/getUserByEmail")
	public ResponseEntity<UserDto> getUserByEmail(@RequestBody String email){
		return service.getUserByEmail(email);
	}
	
	@GetMapping("/getUserbyId/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
		return service.getUserById(id);
	}
}
