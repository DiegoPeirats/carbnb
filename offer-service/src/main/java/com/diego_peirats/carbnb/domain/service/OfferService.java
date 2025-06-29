package com.diego_peirats.carbnb.domain.service;

import org.springframework.http.ResponseEntity;

import DTOs.OfferDto;
import requests.OfferContractRequest;
import requests.OfferCreationRequest;


public interface OfferService {
	
	ResponseEntity<OfferDto> createOffer(OfferCreationRequest request);
	
	ResponseEntity<OfferDto> updateOffer(OfferCreationRequest request);
	
	void deleteOffer(Long id);
	
	ResponseEntity<OfferDto> getOffer(Long id);
	
	ResponseEntity<String> contractOffer(OfferContractRequest request);

}
