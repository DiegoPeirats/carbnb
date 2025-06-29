package com.example.demo.application.security;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.infrastructure.client.UserClient;
import com.example.demo.infrastructure.request.SignUpRequest;

import DTOs.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import requests.LoginRequest;
import requests.UserCreationRequest;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final JWTService jwtService;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final UserClient userClient;
	
	private final ModelMapper modelMapper;
	
	
	public UserDto signUp(@Valid SignUpRequest request) {
		
		ResponseEntity<UserDto> response = userClient.getUserByEmail(request.getEmail());
		
		if (!response.equals(ResponseEntity.noContent().build())) 
			throw new RuntimeException("This email is already in use");
		
		request.setPassword(passwordEncoder.encode(request.getPassword()));
		
		return userClient.createUser(modelMapper.map(request, 
				UserCreationRequest.class)).getBody();
	}
	
	public String[] login(LoginRequest request ) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		UserDto user = (UserDto) authentication.getPrincipal();
		
		String arr[] = new String[2];
		
		arr[0] = jwtService.generateAccessToken(user);
		arr[1] = jwtService.generateRefreshToken(user);
		
		return arr;
	}
	
	public String refreshToken(String refreshToken) {
		Long id = jwtService.getUserIdFromToken(refreshToken);
		
		ResponseEntity<UserDto> response = userClient.getUserById(id);
		
		if (!response.equals(ResponseEntity.noContent().build())) 
			throw new ResourceNotFoundException("USer not found with id:"+id);
		
		return jwtService.generateAccessToken(response.getBody());
	}

}

