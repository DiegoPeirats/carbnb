package com.example.demo.domain.service;

import requests.PaymentRequest;

public interface PaymentService {
	
	String doPayment(PaymentRequest request);

}
