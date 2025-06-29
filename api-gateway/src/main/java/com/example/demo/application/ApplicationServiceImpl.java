package com.example.demo.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.domain.ApplicationService;
import com.example.demo.infrastructure.client.UserClient;
import com.example.demo.infrastructure.client.VehicleClient;
import com.example.demo.infrastructure.client.OfferClient;
import com.example.demo.infrastructure.client.TransactionClient;

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
import user.UserStatus;

@Service
public class ApplicationServiceImpl implements ApplicationService{
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private VehicleClient vehicleClient;
	
	@Autowired
	private OfferClient offerClient;
	
	@Autowired
	private TransactionClient transactionClient;

	@Override
	public ResponseEntity<UserDto> createUser(UserCreationRequest request) {
		return userClient.createUser(request);
	}

	@Override
	public ResponseEntity<UserDto> loginUser(LoginRequest request) {
		UserDto user = userClient.getUser(new LoginRequest(request.getEmail(), request.getPassword())).getBody();
		List<VehicleDto> vehicles = vehicleClient.getMyVehicles(user.getId()).getBody();
		user.setVehicles(vehicles);
		List<TransactionDto> transactions = user.getStatus().equals(UserStatus.OWNER) ? 
				transactionClient.getOwnerTransactionList(user.getId()).getBody()
				: transactionClient.getCustomerTransactionList(user.getId()).getBody();
		user.setTransactions(transactions);
		return ResponseEntity.ok(user);
	}

	@Override
	public ResponseEntity<UserDto> updateUser(UserCreationRequest request) {
		return userClient.updateUser(request);
	}

	@Override
	public void deleteUser(LoginRequest request) {
		userClient.deleteUser(request);
		
	}

	@Override
	public ResponseEntity<VehicleDto> createVehicle(VehicleRequest request) {
		return vehicleClient.createVehicle(request);
	}

	@Override
	public ResponseEntity<VehicleDto> updateVehicle(VehicleRequest request) {
		return null;
	}

	@Override
	public void deleteVehicle(Long id) {
		vehicleClient.deleteVehicle(id);
		
	}

	@Override
	public ResponseEntity<List<VehicleDto>> getVehicleList(PriceCalculationRequest request) {
		//obtener el id del usuario
		Long userId = 1l;
		List<TransactionDto> transactions = transactionClient.getCustomerTransactionList(userId).getBody();
		request.setCustomerTransactionHistorial(transactions);
		return vehicleClient.getAvailableVehicles(request);
	}


	@Override
	public ResponseEntity<OfferDto> createOffer(OfferCreationRequest request) {
		return offerClient.createOffer(request);
	}

	@Override
	public void contractOffer(OfferContractRequest request) {
		OfferDto offer = offerClient.getOffer(request.getOfferId()).getBody();
		String ownerStripeId = userClient.getStripeId(offer.getUserId()).getBody();
		request.setOfferId(offer.getUserId());
		request.setOwnerStripeId(ownerStripeId);
		offerClient.contractOffer(request);
		
	}

	@Override
	public ResponseEntity<OfferDto> updateOffer(OfferCreationRequest request) {
		OfferDto offer = offerClient.getOffer(request.getVehicleId()).getBody();
		offerClient.deleteOffer(offer.getId());
		return offerClient.createOffer(request);
	}

	@Override
	public void deleteOffer(Long id) {
		offerClient.deleteOffer(id);
	}

	@Override
	public ResponseEntity<String> activateOwner(UserActivationRequest request) {
		return userClient.activateOwner(request);
	}
}
