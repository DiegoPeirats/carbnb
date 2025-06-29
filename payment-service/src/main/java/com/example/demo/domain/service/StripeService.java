package com.example.demo.domain.service;

import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;

import DTOs.UserDto;
import requests.PaymentRequest;

public interface StripeService {
	
	String createCustomer(UserDto user) throws StripeException;
	
	String createConnectAccount(UserDto user) throws StripeException;

	SessionCreateParams createSession(PaymentRequest request);

}
