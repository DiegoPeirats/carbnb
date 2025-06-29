package com.diego_peirats.carbnb.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego_peirats.carbnb.domain.entity.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>{
	
	Optional<Offer> findByVehicleId(Long vehicleId);

}
