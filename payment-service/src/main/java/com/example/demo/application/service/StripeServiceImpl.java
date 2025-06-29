package com.example.demo.application.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Customer;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import DTOs.UserDto;
import requests.PaymentRequest;

@Service
public class StripeServiceImpl implements StripeService {

	@Override
	public String createCustomer(UserDto user) throws StripeException {
		CustomerCreateParams params = CustomerCreateParams.builder()
				.setName(user.getFirstName() + " " + user.getLastName())
				.setEmail(user.getEmail())
				.build();
		
		Customer customer = Customer.create(params);
		return customer.getId();
	}

	@Override
	public String createConnectAccount(UserDto user) throws StripeException {
		AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setEmail(user.getEmail())
                .build();
        Account account = Account.create(params);
        return account.getId();
	}

	@Override
	public SessionCreateParams createSession(PaymentRequest request) {
		return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                .setCustomer(request.getCustomerStripeId())
                .setSuccessUrl(request.getSuccessUrl())
                .setCancelUrl(request.getFailureUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("EUR")
                                                .setUnitAmount(request.getPrice())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Alquiler de vehículo")
                                                                .setDescription("Booking Id: "+ request.getOfferId())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .setPaymentIntentData(
                	    SessionCreateParams.PaymentIntentData.builder()
                	        .setTransferData(
                	            SessionCreateParams.PaymentIntentData.TransferData.builder()
                	                .setDestination(request.getOwnerStripeId())
                	                .build()
                	        )
                	        .setApplicationFeeAmount(15L) // comisión
                	        .build()
                	)
                .build();
	}
	
	

}
