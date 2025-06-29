package com.example.demo.infrastructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.ApplicationServiceImpl;

import DTOs.OfferDto;
import DTOs.UserDto;
import DTOs.VehicleDto;
import requests.LoginRequest;
import requests.OfferContractRequest;
import requests.OfferCreationRequest;
import requests.PriceCalculationRequest;
import requests.UserActivationRequest;
import requests.UserCreationRequest;
import requests.VehicleRequest;

@RestController
@RequestMapping("/carbnb")
public class ApplicationController {
	
	@Autowired
	private ApplicationServiceImpl service;
	
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(UserCreationRequest request) {
		return service.createUser(request);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> loginUser(LoginRequest request) {
		return service.loginUser(request);
	}

	@PostMapping("/updateUser")
	public ResponseEntity<UserDto> updateUser(UserCreationRequest request) {
		return service.updateUser(request);
	}

	@PostMapping("/deleteUser")
	public void deleteUser(LoginRequest request) {
		service.deleteUser(request);
		
	}

	@PostMapping("/createVehicle")
	public ResponseEntity<VehicleDto> createVehicle(VehicleRequest request) {
		return service.createVehicle(request);
	}

	@PostMapping("/updateVehicle")
	public ResponseEntity<VehicleDto> updateVehicle(VehicleRequest request) {
		return null;
	}

	@PostMapping("/deleteVehicle")
	public void deleteVehicle(Long id) {
		service.deleteVehicle(id);
		
	}
	
	@GetMapping("/availableVehicles")
	public ResponseEntity<List<VehicleDto>> getAvailableVehicles(PriceCalculationRequest request){
		return service.getVehicleList(request);
	}

	@PostMapping("/createOffer")
	public ResponseEntity<OfferDto> createOffer(OfferCreationRequest request) {
		return service.createOffer(request);
	}

	@PostMapping("/contractOffer")
	public void contractOffer(OfferContractRequest request) {
		service.contractOffer(request);
		
	}

	@PostMapping("/updateOffer")
	public ResponseEntity<OfferDto> updateOffer(OfferCreationRequest request) {
		return service.createOffer(request);
	}

	@PostMapping("/deleteOffer")
	public void deleteOffer(Long id) {
		service.deleteOffer(id);
	}

	@PostMapping("/activateOwner")
	public ResponseEntity<String> activateOwner(UserActivationRequest request) {
		return service.activateOwner(request);
	}

}
