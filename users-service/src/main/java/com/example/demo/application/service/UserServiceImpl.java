package com.example.demo.application.service;

import java.net.URI;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.demo.application.utils.ServiceUtils.*;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.UserService;
import com.example.demo.infrastructure.client.StripeClient;
import com.example.demo.infrastructure.repository.UserRepository;
import DTOs.UserDto;
import requests.LoginRequest;
import requests.UserActivationRequest;
import requests.UserCreationRequest;
import user.UserStatus;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StripeClient stripeClient;

	@Override
	public ResponseEntity<UserDto> createUser(UserCreationRequest request, URI location) {
		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.gender(request.getGender())
				.address(request.getAddress())
				.stateOfOrigin(request.getStateOfOrigin())
				.accountNumber(request.getAccountNumber())
				.email(request.getEmail())
				.password(request.getPassword())
				.phoneNumber(request.getPhoneNumber())
				.profileImage(request.getProfileImage())
				.status(UserStatus.CUSTOMER)
				.owner(false)
				.build();
		
		UserDto dto = modelMapper.map(user, UserDto.class);
		
		user.setStripeId(stripeClient.createCustomer(dto).getBody());
		
		repository.save(user);
		return ResponseEntity.created(location).body(modelMapper.map(user, UserDto.class));
	}

	@Override
	public ResponseEntity<UserDto> updateUser(UserCreationRequest request) {
	    return repository.findByEmail(request.getEmail())
	            .map(user -> {
	            	applyUpdates(user, request);

	                repository.save(user);

	                return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
	            })
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Override
	public void deleteUser(LoginRequest request) {
		Optional<User> optionalUser = repository.findByEmail(request.getEmail());
		if (optionalUser.isPresent())
			if (optionalUser.get().getPassword().equals(request.getPassword()))
				repository.delete(optionalUser.get());
		
	}

	@Override
	public ResponseEntity<UserDto> getUser(LoginRequest request) {
		Optional<User> optionalUser = repository.findByEmail(request.getEmail());
		if (optionalUser.isPresent())
			return ResponseEntity.ok(modelMapper.map(optionalUser, UserDto.class));
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<String> activateOwnership(UserActivationRequest request) {
		Optional<User> user = repository.findById(request.getId());
		
		if (user.isEmpty()) return ResponseEntity.notFound().build();
		
		user.get().setStatus(UserStatus.OWNER);
		user.get().setModifiedAt(request.getCreatedAt());
		
		UserDto dto = modelMapper.map(user, UserDto.class);
		
		user.get().setStripeId(stripeClient.createConnectAccount(dto).getBody());
		
		repository.save(user.get());
		
		return ResponseEntity.ok("Usuario propietario activado");
	}

	public ResponseEntity<String> getEmail(Long id) {

		Optional<User> optional = repository.findById(id);
		if (optional.isEmpty()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(optional.get().getEmail());
	}

	public ResponseEntity<String> getStripeId(Long id) {
		Optional<User> optional = repository.findById(id);
		if (optional.isEmpty()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(optional.get().getStripeId());
	}
	
	public ResponseEntity<UserDto> getUserByEmail(String email){
		Optional<UserDto> optional = repository.findByEmail(email).map(user -> modelMapper.map(user, UserDto.class));
		
		if (optional.isEmpty()) return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok(optional.get());
	}

	public ResponseEntity<UserDto> getUserById(Long id) {
		Optional<UserDto> optional = repository.findById(id).map(
				user -> modelMapper.map(user, UserDto.class));
		
		if (optional.isEmpty()) return ResponseEntity.noContent().build();
				
		return ResponseEntity.ok(optional.get());
	}

	

}
