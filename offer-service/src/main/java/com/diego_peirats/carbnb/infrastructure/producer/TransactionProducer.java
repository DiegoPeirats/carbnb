package com.diego_peirats.carbnb.infrastructure.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import event.TransactionEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionProducer {
	
	private NewTopic topic;
	
	private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

	public TransactionProducer(NewTopic topic, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(TransactionEvent event) {
		log.info("Transaction event => " + event.toString());
		
		Message<TransactionEvent> message = MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, topic.name())
				.build();
		
		kafkaTemplate.send(message);
	}

}
