package com.example.demo.infrastructure.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import DTOs.OfferDto;
import requests.OfferContractRequest;
import requests.OfferCreationRequest;

@FeignClient(name="offer-client", path="/offer-client")
public interface OfferClient {
	
	@PostMapping("/create")
	ResponseEntity<OfferDto> createOffer(OfferCreationRequest request);
	
	@PostMapping("/contract")
	ResponseEntity<String> contractOffer(OfferContractRequest request);
	
	@GetMapping("/getOffer/{id}")
	ResponseEntity<OfferDto> getOffer(@PathVariable Long id);

	@GetMapping("/getAllOffers/{id}")
	ResponseEntity<List<OfferDto>> getMyOffers(@PathVariable Long id);
	
	@PutMapping("/updateOffer")
	ResponseEntity<OfferDto> updateOffer(@RequestBody OfferCreationRequest request);
	
	@DeleteMapping("/deleteOffer/{id}")
	ResponseEntity<Void> deleteOffer(@PathVariable Long id);
}
