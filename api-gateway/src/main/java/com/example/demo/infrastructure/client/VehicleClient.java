package com.example.demo.infrastructure.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import DTOs.VehicleDto;
import requests.PriceCalculationRequest;
import requests.VehicleRequest;

@FeignClient(name="vehicle-service", path="/vehicle-app")
public interface VehicleClient {

	@GetMapping("/api/v1/vehicles/{id}")
	ResponseEntity<List<VehicleDto>> getMyVehicles(@PathVariable Long id);
	
	@PostMapping("/api/v1/vehicle/create")
	ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleRequest request);
	
	@DeleteMapping("/api/v1/deleteVehicle/{id}")
	void deleteVehicle(Long id); 
	
	@PostMapping("/getAvailableVehicles")
	ResponseEntity<List<VehicleDto>> getAvailableVehicles(PriceCalculationRequest request);
	
	@PostMapping("/getAvailableVehicles/{location}")
	public ResponseEntity<List<VehicleDto>> getAvailableVehiclesByLocation(@PathVariable String location, @RequestBody PriceCalculationRequest request);
}
