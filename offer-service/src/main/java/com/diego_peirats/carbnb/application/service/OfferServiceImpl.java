package com.diego_peirats.carbnb.application.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diego_peirats.carbnb.domain.entity.Offer;
import com.diego_peirats.carbnb.domain.service.OfferService;
import com.diego_peirats.carbnb.infrastructure.client.PaymentClient;
import com.diego_peirats.carbnb.infrastructure.producer.TransactionProducer;
import com.diego_peirats.carbnb.infrastructure.repository.OfferRepository;

import DTOs.OfferDto;
import event.TransactionEvent;
import offer.OfferStatus;
import requests.OfferContractRequest;
import requests.OfferCreationRequest;
import requests.PaymentRequest;

@Service
public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepository repository;
	
	@Autowired
	private TransactionProducer producer;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PaymentClient paymentClient;
	
	@Value("${frontend.url}")
    private String frontendUrl;

	@Override
	public ResponseEntity<OfferDto> createOffer(OfferCreationRequest request) {
		Offer offer = Offer.builder()
				.vehicleId(request.getVehicleId())
				.userId(request.getUserId())
				.pricePerDay(request.getPricePerDay())
				.status(OfferStatus.AVAILABLE)
				.build();
		
		Offer saved = repository.save(offer);

		return ResponseEntity.ok(modelMapper.map(saved, OfferDto.class));
	}

	@Override
	public ResponseEntity<OfferDto> updateOffer(OfferCreationRequest request) {
		Optional<Offer> offer = repository.findByVehicleId(request.getVehicleId());
		
		if (offer.isEmpty()) return ResponseEntity.notFound().build();
		
		Offer offerFound = offer.get();
		
		if (!request.getPricePerDay().equals(offerFound.getPricePerDay()) && request.getPricePerDay() != null) 
			offerFound.setPricePerDay(request.getPricePerDay());
		if (!request.getStatus().equals(offerFound.getStatus()) && request.getStatus() != null)
			offerFound.setStatus(request.getStatus());
		
		repository.save(offerFound);
		
		return ResponseEntity.ok(modelMapper.map(offerFound, OfferDto.class));
	}

	@Override
	public void deleteOffer(Long vehicleId) {
		repository.findByVehicleId(vehicleId)
		.ifPresent(offer -> repository.delete(offer));
		
	}

	@Override
	public ResponseEntity<OfferDto> getOffer(Long id) {
		Optional<Offer> opt = repository.findByVehicleId(id);
		
		if (opt.isPresent()) return ResponseEntity.ok(modelMapper.map(opt.get(), OfferDto.class));
		
		return ResponseEntity.notFound().build();
	}
	
	@Override
	public ResponseEntity<String> contractOffer(OfferContractRequest request) {
		Optional<Offer> offer = repository.findById(request.getOfferId());
		
		if (offer.isEmpty()) return ResponseEntity.notFound().build();
		
		Offer offerFound = offer.get();
		Double price = (double) (offerFound.getPricePerDay() * request.getDaysOfContract());

		paymentClient.doPayment(PaymentRequest.builder()
									.customerStripeId(request.getCustomerStripeId())
									.ownerStripeId(request.getOwnerStripeId())
									.offerId(request.getOfferId())
									.price(request.getPrice())
									.successUrl(frontendUrl + "/payment/success")
									.failureUrl(frontendUrl + "/payment/failure")
									.build());
		
		offer.get().setStatus(OfferStatus.CONTRACTED);
		
		repository.save(offerFound);
		
		producer.sendMessage(TransactionEvent.builder()
								.vehicleId(offerFound.getVehicleId())
								.ownerId(offerFound.getUserId())
								.customerId(request.getCustomerId())
								.totalPrice(price)
								.build());
		
		//llamar al servicio de alertas
		
		return ResponseEntity.ok("Oferta contratada");
	}

}
