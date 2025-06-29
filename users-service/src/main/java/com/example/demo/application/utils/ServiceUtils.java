package com.example.demo.application.utils;

import java.util.Optional;

import com.example.demo.domain.entity.User;

import requests.UserCreationRequest;

public class ServiceUtils {
	
	public static void applyUpdates(User user, UserCreationRequest request) {
	    Optional.ofNullable(request.getFirstName()).ifPresent(user::setFirstName);
	    Optional.ofNullable(request.getLastName()).ifPresent(user::setLastName);
	    Optional.ofNullable(request.getGender()).ifPresent(user::setGender);
	    Optional.ofNullable(request.getAddress()).ifPresent(user::setAddress);
	    Optional.ofNullable(request.getStateOfOrigin()).ifPresent(user::setStateOfOrigin);
	    Optional.ofNullable(request.getAccountNumber()).ifPresent(user::setAccountNumber);
	    Optional.ofNullable(request.getEmail()).ifPresent(user::setEmail);
	    Optional.ofNullable(request.getPassword()).ifPresent(user::setPassword);
	    Optional.ofNullable(request.getPhoneNumber()).ifPresent(user::setPhoneNumber);
	}

}
