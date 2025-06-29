package com.example.demo.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import DTOs.AlertDto;
import event.AlertEvent;

public interface AlertService {
	
	AlertDto createAlert(AlertEvent event);
	
	ResponseEntity<List<AlertDto>> getAlerts(Long id);
	
	void deleteAlert(Long id);
	
	void sendAlert(AlertDto alert);

}
