package com.diego_peirats.carbnb.application.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diego_peirats.carbnb.application.priceStrategy.StrategyService;
import com.diego_peirats.carbnb.application.priceStrategy.StrategyUtils;
import com.diego_peirats.carbnb.domain.entity.Vehicle;
import com.diego_peirats.carbnb.domain.service.VehicleService;
import com.diego_peirats.carbnb.infrastructure.producer.OfferProducer;
import com.diego_peirats.carbnb.infrastructure.repository.VehicleRepository;

import DTOs.VehicleDto;
import event.OfferEvent;
import lombok.RequiredArgsConstructor;
import offer.OfferStatus;
import requests.PriceCalculationRequest;
import requests.VehicleRequest;
import vehicle.StatusType;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{
	
	private final VehicleRepository repository;
	
	private final OfferProducer producer;
	
	private final ModelMapper modelMapper;
	
	private final StrategyService priceService;

	@Override
	public ResponseEntity<VehicleDto> createVehicle(VehicleRequest request, URI location) {
		
		Vehicle vehicle = Vehicle.builder()
				.address(request.getAddress())
				.brand(request.getBrand())
				.year(request.getYear())
				.type(request.getType())
				.ownerId(request.getOwnerId())
				.status(request.getStatus())
				.kms(request.getKms())
				.image1(request.getImage1())
				.image2(request.getImage2())
				.image3(request.getImage3())
				.image4(request.getImage4())
				.build();
		
		repository.save(vehicle);
		
		return ResponseEntity.ok(modelMapper.map(vehicle, VehicleDto.class));
	}

	@Override
	public ResponseEntity<VehicleDto> updateVehicle(VehicleRequest request) {
		
		Optional<Vehicle> vehicle = repository.findByPlate(request.getPlate());
		
		if (vehicle.isEmpty()) return ResponseEntity.notFound().build();

		if (request.getStatus().equals(StatusType.BLOCKED)) producer.sendMessage(
				new OfferEvent(vehicle.get().getId(), request.getOwnerId(), OfferStatus.BLOCKED));
		
		repository.save(vehicle.get());
		
		return ResponseEntity.ok(modelMapper.map(vehicle, VehicleDto.class));
	}

	@Override
	public void deleteVehicle(Long id) {
		Optional<Vehicle> vehicle = repository.findById(id);

		if(vehicle.isPresent()) repository.delete(vehicle.get());
		
	}

	@Override
	public ResponseEntity<VehicleDto> getVehicle(Long id) {
		Optional<Vehicle> vehicle = repository.findById(id);
		if(vehicle.isPresent()) return ResponseEntity.ok(modelMapper.map(vehicle.get(), VehicleDto.class));
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<VehicleDto>> getAvailableVehicles(PriceCalculationRequest request) {
	    return getAvailableVehiclesByFilter(null, request);
	}

	@Override
	public ResponseEntity<List<VehicleDto>> getAvailableVehiclesByLocation(String location, PriceCalculationRequest request) {
	    return getAvailableVehiclesByFilter(location, request);
	}

	private ResponseEntity<List<VehicleDto>> getAvailableVehiclesByFilter(String location, PriceCalculationRequest request) {
	    List<Vehicle> vehicles = repository.findByStatus(StatusType.AVAILABLE);

	    if (vehicles.isEmpty()) 
	        return ResponseEntity.noContent().build();

	    Stream<Vehicle> stream = vehicles.stream();
	    if (location != null) 
	        stream = stream.filter(vehicle -> vehicle.getAddress().equalsIgnoreCase(location));

	    List<VehicleDto> dtos = stream
	            .map(vehicle -> modelMapper.map(vehicle, VehicleDto.class))
	            .toList();
	    
	    dtos.stream()
	    .forEach(dto ->{
	    	request.setVehicleId(dto.getId());
	    	dto.setPrice(priceService.calculatePrice(request));
	    });

	    return ResponseEntity.ok(dtos);
	}


}
