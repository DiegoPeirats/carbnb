package com.example.demo.infrastructure.controller;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.security.AuthService;
import com.example.demo.infrastructure.request.SignUpRequest;

import DTOs.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import requests.LoginRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpRequest signUpRequest, 
			HttpServletRequest request, HttpServletResponse response){
		return new ResponseEntity<>(service.signUp(signUpRequest), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest,
			HttpServletRequest request, HttpServletResponse response){
		
		String[] tokens = service.login(loginRequest);
		
		Cookie cookie = new Cookie("refreshToken", tokens[1]);
		cookie.setHttpOnly(true);
		
		response.addCookie(cookie);
		
		return ResponseEntity.ok(tokens[0]);
	}
	
    @PostMapping("/refresh")
    public ResponseEntity<String> signUp(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie-> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = service.refreshToken(refreshToken);

        return ResponseEntity.ok(accessToken);
    }

}
