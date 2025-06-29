package com.example.demo.application.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.PaymentService;
import com.example.demo.domain.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import DTOs.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import requests.PaymentRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private StripeService stripeService;

	@Override
	public String doPayment(PaymentRequest request) {
		try {
			
			SessionCreateParams sessionParams = stripeService.createSession(request);
            
        	Session session = Session.create(sessionParams);

        	log.info("Sesión creada con éxito para la reserva con id: {}", request.getOfferId());
        	return session.getUrl();
		}catch(StripeException e) {
			throw new RuntimeException(e);
		}
	}


}
