package com.diego_peirats.carbnb.application.priceStrategy;

import org.springframework.stereotype.Service;

import com.diego_peirats.carbnb.domain.entity.Vehicle;

import lombok.RequiredArgsConstructor;
import requests.OfferContractRequest;
import requests.PriceCalculationRequest;

@Service
@RequiredArgsConstructor
public class StrategyService {
	
	private final StrategyUtils utils;
	
	public Long calculatePrice(PriceCalculationRequest request) {
	    Vehicle vehicle = utils.getVehicle(request.getVehicleId());

	    double basePrice = vehicle.getBasePrice();

	    if (utils.isHighDemand()) {
	        basePrice *= 1.1;
	    }

	    if (StrategyUtils.isInNext7Days(request.getInitialDate())) {
	        basePrice *= 1.1;
	    }

	    if (StrategyUtils.isHolidays(request.getInitialDate(), request.getFinalDate())) {
	        basePrice *= 1.1;
	    }

	    if (StrategyUtils.isVipClient(request.getCustomerTransactionHistorial())) {
	        basePrice /= 1.03;
	    }

	    return Math.round(basePrice);
	}
	

}
