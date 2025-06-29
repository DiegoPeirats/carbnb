package com.diego_peirats.carbnb.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.carbnb.application.service.OfferServiceImpl;

import DTOs.OfferDto;
import requests.OfferContractRequest;
import requests.OfferCreationRequest;

@RestController
public class OfferController {
	
	@Autowired
	private OfferServiceImpl service;

	@PostMapping("/create")
	public ResponseEntity<OfferDto> createOffer(OfferCreationRequest request) {
		return service.createOffer(request);
	}
	
	@GetMapping("/getOffer/{id}")
	public ResponseEntity<OfferDto> getOffer(@PathVariable Long vehicleId){
		return service.getOffer(vehicleId);
	}
	
	@PutMapping("/updateOffer")
	public ResponseEntity<OfferDto> updateOffer(@RequestBody OfferCreationRequest request){
		return service.updateOffer(request);
	}
	
	@DeleteMapping("/deleteOffer/{id}")
	public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
		try {
			service.deleteOffer(id);
			return ResponseEntity.accepted().build();
		}
		catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/contract")
	public ResponseEntity<String> contractOffer(@RequestBody OfferContractRequest request){
		return service.contractOffer(request);
	}


}
