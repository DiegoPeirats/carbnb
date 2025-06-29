package com.diego_peirats.carbnb.infrastructure.listener;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.diego_peirats.carbnb.application.service.OfferServiceImpl;

import event.OfferEvent;
import offer.OfferStatus;
import requests.OfferCreationRequest;


@Service
public class OfferKafkaListener {
	
	private static final Logger LOGGER =LoggerFactory.getLogger(OfferKafkaListener.class);
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	@Autowired
	private OfferServiceImpl service;
	
	@KafkaListener(
			topics = "${spring.kafka.topic.name}",
			groupId = "${spring.kafka.consumer.group-id}"
		)
	public void consume(OfferEvent event) {
		LOGGER.info("Transaccion recibida => " + event.toString());
		if(event.getStatus().equals(OfferStatus.BLOCKED))
			service.updateOffer(OfferCreationRequest.builder()
									.vehicleId(event.getVehicleId())
									.userId(event.getUserId())
									.status(event.getStatus())
									.build());
		
		latch.countDown();
		
	}
	public CountDownLatch getLatch() {
        return latch;
    }

}
