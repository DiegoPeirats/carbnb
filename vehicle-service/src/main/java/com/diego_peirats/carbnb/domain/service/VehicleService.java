package com.diego_peirats.carbnb.domain.service;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;

import DTOs.VehicleDto;
import requests.PriceCalculationRequest;
import requests.VehicleRequest;

public interface VehicleService {
	
	ResponseEntity<VehicleDto> createVehicle(VehicleRequest request, URI location);
	ResponseEntity<VehicleDto> updateVehicle(VehicleRequest request);
	void deleteVehicle(Long id);
	ResponseEntity<VehicleDto> getVehicle(Long id);
	ResponseEntity<List<VehicleDto>> getAvailableVehicles(PriceCalculationRequest request);
	ResponseEntity<List<VehicleDto>> getAvailableVehiclesByLocation(String location, PriceCalculationRequest request);

}
