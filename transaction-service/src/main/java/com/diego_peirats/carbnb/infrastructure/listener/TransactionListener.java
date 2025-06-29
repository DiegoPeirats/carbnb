package com.diego_peirats.carbnb.infrastructure.listener;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.diego_peirats.carbnb.domain.entity.Transaction;
import com.diego_peirats.carbnb.infrastructure.repository.TransactionRepository;

import event.TransactionEvent;

@Service
public class TransactionListener {
	
	private static final Logger LOGGER =LoggerFactory.getLogger(TransactionListener.class);
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	@Autowired
	private TransactionRepository repository;
	
	@KafkaListener(
			topics = "${spring.kafka.topic.name}",
			groupId = "${spring.kafka.consumer.group-id}"
		)
	public void consume(TransactionEvent event) {
		LOGGER.info("Transaccion recibida");
		Transaction transaction = Transaction.builder()
				.vehicleId(event.getVehicleId())
				.ownerId(event.getOwnerId())
				.customerId(event.getCustomerId())
				.totalPrice(event.getTotalPrice())
				.build();
		repository.save(transaction);
		latch.countDown();
		
	}
	public CountDownLatch getLatch() {
        return latch;
    }

}