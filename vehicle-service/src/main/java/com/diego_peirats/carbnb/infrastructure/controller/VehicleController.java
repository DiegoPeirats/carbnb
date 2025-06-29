package com.diego_peirats.carbnb.infrastructure.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.diego_peirats.carbnb.application.service.VehicleServiceImpl;

import DTOs.VehicleDto;
import requests.PriceCalculationRequest;
import requests.VehicleRequest;

@RestController
public class VehicleController {
	
	@Autowired
	private VehicleServiceImpl service;
	
	@PostMapping("vehicle/create")
	public ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleRequest request) {
		 URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .buildAndExpand() 
	                .toUri();
		return service.createVehicle(request, location);
	}
	
	@GetMapping("/getVehicle/{id}")
	public ResponseEntity<VehicleDto> getVehicle(@PathVariable Long id){
		return service.getVehicle(id);
	}
	
	@PostMapping("/getAvailableVehicles")
	public ResponseEntity<List<VehicleDto>> getAvailableVehicles(PriceCalculationRequest request){
		return service.getAvailableVehicles(request);
	}
	
	@PostMapping("/getAvailableVehicles/{location}")
	public ResponseEntity<List<VehicleDto>> getAvailableVehiclesByLocation(@PathVariable String location, @RequestBody PriceCalculationRequest request){
		return service.getAvailableVehiclesByLocation(location, request);
	}
	
	@PutMapping("/updateVehicle")
	public ResponseEntity<VehicleDto> updateVehicle(@RequestBody VehicleRequest request){
		return service.updateVehicle(request);
	}
	
	@DeleteMapping("/deleteVehicle/{id}")
	public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
		try {
			service.deleteVehicle(id);
			return ResponseEntity.accepted().build();
		}
		catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
