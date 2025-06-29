package com.example.demo.infrastructure.listener;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.application.AlertServiceImpl;

import DTOs.AlertDto;
import event.AlertEvent;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AlertListener {
	
	@Autowired
	private AlertServiceImpl service;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	@KafkaListener(
			topics = "${spring.kafka.topic.name}",
			groupId = "${spring.kafka.consumer.group-id}"
		)
	public void consume(AlertEvent event) {
		log.info(String.format("Alert event recieved in stock service => %s", event.toString()));
		AlertDto alert = service.createAlert(event);
		
		service.sendAlert(alert);
	}
	
	public CountDownLatch getLatch() {
        return latch;
    }
}
