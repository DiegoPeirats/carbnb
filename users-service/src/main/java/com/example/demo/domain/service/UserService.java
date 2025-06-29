package com.example.demo.domain.service;

import java.net.URI;

import org.springframework.http.ResponseEntity;


import DTOs.UserDto;
import requests.LoginRequest;
import requests.UserActivationRequest;
import requests.UserCreationRequest;

public interface UserService {
	
	
	ResponseEntity<UserDto> createUser(UserCreationRequest request, URI location);
	ResponseEntity<UserDto> updateUser(UserCreationRequest request);
	void deleteUser(LoginRequest request);
	ResponseEntity<UserDto> getUser(LoginRequest request);
	ResponseEntity<String> activateOwnership(UserActivationRequest request);
	
	

}
