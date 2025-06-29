package com.diego_peirats.carbnb.infrastructure.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import event.OfferEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OfferProducer {
	
	private NewTopic topic;
	
	private KafkaTemplate<String, OfferEvent> kafkaTemplate;

	public OfferProducer(NewTopic topic, KafkaTemplate<String, OfferEvent> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(OfferEvent event) {
		log.info("Offer event => " + event.toString());
		
		Message<OfferEvent> message = MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, topic.name())
				.build();
		
		kafkaTemplate.send(message);
	}

}
