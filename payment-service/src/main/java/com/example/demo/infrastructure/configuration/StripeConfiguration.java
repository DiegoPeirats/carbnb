package com.example.demo.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfiguration {

	@Value("${stripe.secret-key}")
	private String secretKey;
	
	@PostConstruct
	public void init() {
		Stripe.apiKey = secretKey;
	}
}
