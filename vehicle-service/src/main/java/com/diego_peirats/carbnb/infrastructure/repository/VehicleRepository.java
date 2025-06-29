package com.diego_peirats.carbnb.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego_peirats.carbnb.domain.entity.Vehicle;

import vehicle.StatusType;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
	
	Optional<Vehicle> findByPlate(String plate);
	
	List<Vehicle> findByStatusEqualsIgnoreCase(StatusType status);


}
