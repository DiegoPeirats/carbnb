package com.example.demo.application;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import DTOs.AlertDto;

public class WebSocketPublisher {
	
	private final SimpMessagingTemplate template;

	public WebSocketPublisher(SimpMessagingTemplate template) {
		this.template = template;
	}
	
    public void sendAlert(AlertDto message) {
        template.convertAndSend("/topic/alerts", message);
        System.out.println("📡 Mensaje enviado a WebSocket: " + message);
    }

}
