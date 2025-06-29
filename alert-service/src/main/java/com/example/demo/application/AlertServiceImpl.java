package com.example.demo.application;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.Alert;
import com.example.demo.domain.service.AlertService;
import com.example.demo.infrastructure.client.EmailClient;
import com.example.demo.infrastructure.client.UserClient;
import com.example.demo.infrastructure.repository.AlertRepository;

import DTOs.AlertDto;
import email.EmailDetails;
import event.AlertEvent;

@Service
public class AlertServiceImpl implements AlertService{
	
	@Autowired
	private AlertRepository repository;
	
	@Autowired
	private WebSocketPublisher publisher;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private EmailClient emailClient;
	
	
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AlertDto createAlert(AlertEvent event) {

		Alert alert = Alert.builder()
				.userId(event.getUserId())
				.type(event.getType())
				.build();

		repository.save(alert);
		return modelMapper.map(alert, AlertDto.class);
	}

	@Override
	public ResponseEntity<List<AlertDto>> getAlerts(Long userId) {
		List<Alert> alerts = repository.findAllByUserId(userId);
		
		List<AlertDto> list = alerts.stream()
				.map(alert -> modelMapper.map(alert, AlertDto.class))
				.toList();
		return ResponseEntity.ok(list);
	}

	@Override
	public void deleteAlert(Long id) {
		Optional<Alert> optional = repository.findById(id);
		if (optional.isPresent()) repository.delete(optional.get());
		
	}

	@Override
	public void sendAlert(AlertDto alert) {
		if (alert != null) {
			publisher.sendAlert(alert);
			
			String email = userClient.getEmail(alert.getUserId()).getBody();
			
			EmailDetails details = EmailDetails.builder()
					.recipient(email)
					.subject(alert.getType().toString())
					.messageBody(alert.getType().toString())
					.build();
			
			emailClient.simpleMail(details);
			
		}
		
	}

}
