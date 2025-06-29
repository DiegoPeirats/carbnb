package com.diego_peirats.carbnb.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import requests.PaymentRequest;

@FeignClient(name="payment-client", path="/payment-client")
public interface PaymentClient {
	
	@PostMapping("/payment")
	ResponseEntity<String> doPayment(PaymentRequest request);

}
