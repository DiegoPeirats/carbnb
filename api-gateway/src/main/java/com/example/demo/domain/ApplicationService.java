package com.example.demo.domain;

import java.util.List;

import org.springframework.http.ResponseEntity;

import DTOs.OfferDto;
import DTOs.TransactionDto;
import DTOs.UserDto;
import DTOs.VehicleDto;
import requests.LoginRequest;
import requests.OfferContractRequest;
import requests.OfferCreationRequest;
import requests.PriceCalculationRequest;
import requests.UserActivationRequest;
import requests.UserCreationRequest;
import requests.VehicleRequest;

public interface ApplicationService {
	
	//USER
	ResponseEntity<UserDto> createUser(UserCreationRequest request);
	
	ResponseEntity<UserDto> loginUser(LoginRequest request);
	
	ResponseEntity<UserDto> updateUser(UserCreationRequest request);
	
	void deleteUser(LoginRequest request);
	
	ResponseEntity<String> activateOwner(UserActivationRequest request);
	
	//VEHICLE
	ResponseEntity<VehicleDto> createVehicle(VehicleRequest request);
	
	ResponseEntity<VehicleDto> updateVehicle(VehicleRequest request);
	
	void deleteVehicle(Long id);
	
	ResponseEntity<List<VehicleDto>> getVehicleList(PriceCalculationRequest request);
	
	//OFFER
	ResponseEntity<OfferDto> createOffer(OfferCreationRequest request);
	
	void contractOffer(OfferContractRequest request);
	
	ResponseEntity<OfferDto> updateOffer(OfferCreationRequest request);
	
	void deleteOffer(Long id);
	

}
